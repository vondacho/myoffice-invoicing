package edu.noia.myoffice.invoicing.domain.service.bm;

/**
 * Created by olivier on 07.08.14.
 */
public class DefaultBillingManager {
/*
    @Autowired
    protected OfferRepository offerRepository;

    @Autowired
    protected SubmissionRepository submissionRepository;

    @Override
    @Transactional
    public Invoice invoice(Offer offer) {
        Invoice invoice = new Invoice();
        invoice.setFolder(offer.getFolder());
        invoice.setTaxRate(DEFAULT_TAX_RATE);
        invoice.setDiscountRate(DEFAULT_DISCOUNT_RATE);
        createItems(invoice, offer.getItems());
        createDebtors(invoice);
        collectProvisions(invoice);
        invoiceRepository.save(invoice);
        offer.setInvoice(invoice);
        offerRepository.save(offer);
        return invoice;
    }

    @Override
    @Transactional
    public Invoice invoice(Submission submission) {
        Invoice invoice = new Invoice();
        invoice.setFolder(submission.getFolder());
        invoice.setTaxRate(DEFAULT_TAX_RATE);
        invoice.setDiscountRate(DEFAULT_DISCOUNT_RATE);
        createItems(invoice, submission.getItems());
        createDebtors(invoice);
        collectProvisions(invoice);
        invoiceRepository.save(invoice);
        submission.setInvoice(invoice);
        submissionRepository.save(submission);
        return invoice;
    }
    */
}
