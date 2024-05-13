package com.example.bankkata.domain.service.LivretEpargne;

public interface LivretEpargneService {

    /**
     * Dépose une somme d'argent dans un livret d'épargne identifié par le numéro de compte donné.
     * Le dépôt ne peut excéder le plafond de dépôt du livret.
     * @param accountNumber Le numéro du livret d'épargne où le dépôt est effectué.
     * @param amount Le montant à déposer.
     */
    void deposit(String accountNumber, double amount);

    /**
     * Renvoie le solde actuel du livret d'épargne identifié par le numéro de compte donné.
     * @param accountNumber Le numéro du livret d'épargne pour lequel le solde est requis.
     * @return Le solde actuel du livret d'épargne.
     */
    double getBalance(String accountNumber);
}
