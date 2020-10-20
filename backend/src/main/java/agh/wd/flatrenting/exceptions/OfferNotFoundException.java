package agh.wd.flatrenting.exceptions;

import javax.persistence.EntityNotFoundException;

public class OfferNotFoundException extends EntityNotFoundException {
    public OfferNotFoundException(String id) {
        super("Offer " + id + " not found.");
    }
}
