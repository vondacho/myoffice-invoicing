package edu.noia.myoffice.invoicing.domain.service;

/**
 * Created by olivier on 07.08.14.
 */
public class DefaultBillingManager implements BillingManager {

    /*

    protected static final Double DEFAULT_TAX_RATE = 0.08;
    protected static final Double DEFAULT_DISCOUNT_RATE = 0.0;

    @Autowired
    protected InvoiceRepository invoiceRepository;

    @Autowired
    protected ReceptionRepository receptionRepository;

    @Autowired
    protected ProvisionRepository provisionRepository;

    @Autowired
    protected CustomerRepository customerRepository;

    @Override
    @Transactional
    public Invoice invoice(Folder folder) {
        Invoice invoice = new Invoice();
        invoice.setFolder(folder);
        invoice.setTaxRate(DEFAULT_TAX_RATE);
        invoice.setDiscountRate(DEFAULT_DISCOUNT_RATE);
        createItems(invoice);
        createDebtors(invoice);
        if (!invoice.getItems().isEmpty()) {
            collectProvisions(invoice);
        }
        invoiceRepository.save(invoice);
        return invoice;
    }

    @Override
    @Transactional
    public void save(Invoice invoice) {

    }

    protected void createItems(Invoice invoice) {
        invoice.setItems(new HashSet<InvoiceItem>());
    }

    protected void createItems(Invoice invoice, Collection<? extends InvoiceableItem> invoiceables) {
        invoice.setItems(new HashSet<InvoiceItem>());
        invoice.getItems().addAll(invoice(invoiceables));
    }

    protected List<InvoiceItem> invoice(Collection<? extends InvoiceableItem> invoiceables) {
        List<InvoiceItem> items = new ArrayList<InvoiceItem>();
        for (InvoiceableItem invoiceable : invoiceables) {
            items.add(new InvoiceItem(invoiceable));
        }
        return items;
    }

    protected void createDebtors(Invoice invoice) {
        invoice.setDebtors(new HashSet<InvoiceDebtor>());

        List<Customer> customers = customerRepository.findByFolder(invoice.getFolder());

        double defaultPercentageByPart = 1.0 / customers.size();
        double defaultAmountByPart = invoice.computePrice() * defaultPercentageByPart;

        for (Customer customer : customers) {
            invoice.getDebtors().add(new InvoiceDebtor(invoice, customer, defaultAmountByPart, defaultPercentageByPart));
        }
    }

    protected void collectProvisions(Invoice invoice) {
        for (Provision provision : provisionRepository.findAllAvailableByFolder(invoice.getFolder())) {
            for (InvoiceDebtor debtor : invoice.getDebtors()) {
                if (debtor.getRemainingAmount() > 0.0) {
                    if (debtor.getCustomer().getId().equals(provision.getReception().getCustomer().getId())) {
                        if (debtor.getRemainingAmount() <= provision.getRemainingAmount()) {
                            invoice.getProvisions().add(new InvoicedProvision(invoice, provision, debtor.getRemainingAmount()));
                            provision.setRemainingAmount(provision.getRemainingAmount() - debtor.getRemainingAmount());
                            debtor.setRemainingAmount(0.0);
                        } else {
                            invoice.getProvisions().add(new InvoicedProvision(invoice, provision, provision.getRemainingAmount()));
                            debtor.setRemainingAmount(debtor.getRemainingAmount() - provision.getRemainingAmount());
                            provision.setRemainingAmount(0.0);
                        }
                    }
                }
            }
        }
    }

    @Override
    @Transactional
    public Reception receive(Income income, Customer customer) {
        Reception reception = new Reception();
        reception.setChannel(ReceptionChannel.CASH);
        reception.setCustomer(customer);
        reception.setIncome(income);
        receive(reception);
        return receptionRepository.save(reception);
    }

    @Override
    @Transactional
    public Reception receive(Income income, Folder folder) {
        Reception reception = new Reception();
        reception.setChannel(ReceptionChannel.CASH);
        reception.setCustomer(customerRepository.findByFolder(folder).iterator().next());
        reception.setIncome(income);
        receive(reception);
        return receptionRepository.save(reception);
    }

    @Override
    @Transactional
    public Reception receive(Income income, Ticket ticket) {
        Reception reception = new Reception();
        reception.setChannel(ReceptionChannel.TICKET);
        reception.setTicket(ticket);
        reception.setCustomer(ticket.getCustomer());
        reception.setIncome(income);
        receive(reception);
        return receptionRepository.save(reception);
    }

    @Override
    @Transactional
    public Reception receive(Income income, Ticket ticket, Customer customer) {
        Reception reception = new Reception();
        reception.setChannel(ReceptionChannel.TICKET);
        reception.setCustomer(customer);
        reception.setIncome(income);
        reception.setTicket(ticket);
        receive(reception);
        return receptionRepository.save(reception);
    }

    protected void receive(Reception reception) {
        createProvision(reception, payOpenInvoices(reception));
    }

    protected Provision createProvision(Reception reception, Double remainingIncomingAmount) {
        if (remainingIncomingAmount > 0.0) {
            Provision provision = new Provision();
            provision.setFolder(reception.getCustomer().getFolder());
            provision.setReception(reception);
            provision.setAmount(remainingIncomingAmount);
            provision.setRemainingAmount(provision.getAmount());
            provisionRepository.save(provision);
            return provision;
        }
        else return null;
    }

    protected double payOpenInvoices(Reception reception) {
        Double remainingIncomingAmount = reception.getIncome().getAmount();
        Folder folder = reception.getCustomer().getFolder();
        List<Customer> folderCustomers = promoteItem(reception.getCustomer(), customerRepository.findByFolder(folder));
        List<Invoice> openInvoices = invoiceRepository.findOpenByFolder(folder);
        for (Iterator<Invoice> iterator = openInvoices.iterator(); iterator.hasNext() && remainingIncomingAmount > 0.0; ) {
            Invoice invoice = iterator.next();
            for (Customer customer : folderCustomers) {
                InvoiceDebtor debtor = invoice.getDebtor(customer);
                if (debtor.getRemainingAmount() > 0.0) {
                    InvoicePayment payment = new InvoicePayment();
                    payment.setInvoice(invoice);
                    payment.setReception(reception);
                    payment.setCustomer(debtor);

                    if (debtor.getRemainingAmount() <= remainingIncomingAmount) {
                        payment.setAmount(debtor.getRemainingAmount());
                        remainingIncomingAmount -= debtor.getRemainingAmount();
                        debtor.setRemainingAmount(0.0);
                    } else {
                        payment.setAmount(remainingIncomingAmount);
                        debtor.setRemainingAmount(debtor.getRemainingAmount() - remainingIncomingAmount);
                        remainingIncomingAmount = 0.0;
                    }
                    invoice.getPayments().add(payment);
                    if (invoice.canBeClosed())
                        invoice.setClosureDate(reception.getIncome().getCreatedDate());
                }
            }
            invoiceRepository.save(invoice);
        }
        return remainingIncomingAmount;
    }

    private <E> List<E> promoteItem(final E item, final List<E> items) {
        if (items.contains(item)) {
            List<E> target = new ArrayList(items);
            Collections.sort(target, new Comparator<E>() {
                @Override
                public int compare(E e, E e2) {
                    return e.equals(item) ? -1 : 0;
                }
            });
            return target;
        }
        else return Collections.emptyList();
    }

    @Override
    @Transactional
    public void close(Invoice invoice) {

    }

    @Override
    @Transactional
    public void reopen(Invoice invoice) {

    }

    @Override
    @Transactional
    public Recall recall(Invoice invoice) {
        return null;
    }

    @Override
    @Transactional
    public void delete(Invoice invoice) {
        List<Provision> provisions = new ArrayList<Provision>();
        for (InvoicedProvision iprovision : invoice.getProvisions()) {
            iprovision.getProvision().setRemainingAmount(iprovision.getProvision().getRemainingAmount() + iprovision.getConsumedAmount());
            provisions.add(iprovision.getProvision());
        }
        provisionRepository.save(provisions);
        invoiceRepository.delete(invoice);
    }

    */
}
