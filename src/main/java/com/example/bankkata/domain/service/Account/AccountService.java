package com.example.bankkata.domain.service.Account;

import com.example.bankkata.domain.model.Operation;

import java.util.List;

public interface AccountService {

    /**
     * Dépose une somme spécifiée dans le compte identifié par le numéro de compte donné.
     * @param accountNumber Le numéro de compte sur lequel le dépôt doit être effectué.
     * @param amount Le montant à déposer.
     */
    void deposit(String accountNumber, double amount);

    /**
     * Retire une somme spécifiée du compte identifié par le numéro de compte donné.
     * Cette méthode doit s'assurer que le retrait ne dépasse pas le solde disponible
     * ou le découvert autorisé, le cas échéant.
     * @param accountNumber Le numéro de compte duquel le montant sera retiré.
     * @param amount Le montant à retirer.
     */
    void withdraw(String accountNumber, double amount);

    /**
     * Renvoie le solde actuel du compte identifié par le numéro de compte donné.
     * @param accountNumber Le numéro de compte pour lequel le solde est requis.
     * @return Le solde actuel du compte.
     */
    double getBalance(String accountNumber);

    /**
     * Fournit un relevé des opérations effectuées sur un mois glissant pour le compte
     * spécifié, triées par date dans l'ordre antéchronologique.
     * @param accountNumber Le numéro de compte pour lequel le relevé est demandé.
     * @return Une liste des opérations effectuées sur le compte.
     */
    List<Operation> getMonthlyStatement(String accountNumber);
}
