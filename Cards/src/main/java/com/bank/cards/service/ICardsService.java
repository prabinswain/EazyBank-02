package com.bank.cards.service;

import com.bank.cards.dto.CardsDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

public interface ICardsService {

    /**
     * create card
     * @param mobileNumber  - Mobile Number of the Customer
     */
    void createCard( String mobileNumber);

    CardsDto fetchCard( String mobileNumber);

    /**
     *
     * @param cardsDto - CardsDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    boolean updateCard(CardsDto cardsDto);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of card details is successful or not
     */
    boolean deleteCard(String mobileNumber);
}
