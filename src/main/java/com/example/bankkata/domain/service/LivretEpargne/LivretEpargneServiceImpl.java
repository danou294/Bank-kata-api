package com.example.bankkata.domain.service.LivretEpargne;

import com.example.bankkata.domain.model.LivretEpargne;
import com.example.bankkata.domain.port.LivretEpargneRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LivretEpargneServiceImpl implements LivretEpargneService {

    private final LivretEpargneRepository livretEpargneRepository;

    public LivretEpargneServiceImpl(LivretEpargneRepository livretEpargneRepository) {
        this.livretEpargneRepository = livretEpargneRepository;
    }

    @Override
    public LivretEpargne createLivretEpargne(double plafondDepot) {
        LivretEpargne livretEpargne = new LivretEpargne(plafondDepot);
        return livretEpargneRepository.save(livretEpargne);
    }


@Override
public LivretEpargne getLivretEpargneById(Long id) {
    System.out.println("Recherche du livret d'épargne avec l'ID : " + id);
    Optional<LivretEpargne> optionalLivretEpargne = livretEpargneRepository.findById(id);
    if (optionalLivretEpargne.isPresent()) {
        System.out.println("Livret d'épargne trouvé : " + optionalLivretEpargne.get().getId());
        return optionalLivretEpargne.get();
    } else {
        System.out.println("Aucun livret d'épargne trouvé pour l'ID : " + id);
        return null;
    }
}




    @Override
    public LivretEpargne updatePlafondDepot(Long id, double nouveauPlafond) {
        LivretEpargne livretEpargne = getLivretEpargneById(id);
        if (livretEpargne != null) {
            livretEpargne.setPlafondDepot(nouveauPlafond);
            return livretEpargneRepository.save(livretEpargne);
        }
        return null;
    }

    @Override
    public void deleteLivretEpargne(Long id) {
        livretEpargneRepository.deleteById(id);
    }
}
