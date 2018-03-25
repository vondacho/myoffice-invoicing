package edu.noia.myoffice.invoicing.domain.service.mmb;

/**
 * Created by olivier on 07.08.14.
 */
public class DefaultBillingManager {
/*
    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private HonoraryRepository honoraryRepository;

    @Override
    protected void createItems(Invoice invoice) {
        createItems(invoice, honoraryRepository.findByFolderNullInvoice(invoice.getFolder()));
    }

    @Override
    protected Provision createProvision(Reception reception, Double remainingIncomingSum) {
        Provision provision = super.createProvision(reception, remainingIncomingSum);
        if (provision != null) {
            satisfyProvisioningRequests(provision);
        }
        return provision;
    }

    private void satisfyProvisioningRequests(Provision provision) {
        Double availableSum = provision.getAmount();
        List<Request> openRequests = requestRepository.findOpenByFolder(provision.getReception().getCustomer().getFolder());
        for (Iterator<Request> iterator = openRequests.iterator(); iterator.hasNext() && availableSum > 0.0; ) {
            Request request = iterator.next();
            RequestDebtor debtor = request.getDebtor(provision.getReception().getCustomer());
            if (debtor.getRemainingAmount() > 0.0) {
                RequestedProvision requestedProvision = new RequestedProvision();
                requestedProvision.setProvision(provision);
                requestedProvision.setRequest(request);
                requestedProvision.setAmount(availableSum);

                if (debtor.getRemainingAmount() <= availableSum) {
                    requestedProvision.setAmount(debtor.getRemainingAmount());
                    availableSum -= debtor.getRemainingAmount();
                    debtor.setRemainingAmount(0.0);
                } else {
                    requestedProvision.setAmount(availableSum);
                    debtor.setRemainingAmount(debtor.getRemainingAmount() - availableSum);
                    availableSum = 0.0;
                }
                request.getProvisions().add(requestedProvision);
                if (request.canBeClosed())
                    request.setClosureDate(provision.getReception().getIncome().getCreatedDate());
            }
            requestRepository.save(request);
        }
    }

    @Override
    @Transactional
    public void save(Request request) {

    }

    @Override
    @Transactional
    public void close(Request request) {

    }

    @Override
    @Transactional
    public void reopen(Request request) {

    }

    @Override
    @Transactional
    public Recall recall(Request request) {
        return null;
    }

    @Override
    @Transactional
    public void delete(Request request) {

    }
    */
}
