package com.ii.app.services;

import com.ii.app.dto.in.TransactionTemplateIn;
import com.ii.app.dto.out.TransactionTemplateOut;
import com.ii.app.mappers.TransactionTemplateMapper;
import com.ii.app.models.TransactionTemplate;
import com.ii.app.repositories.TransactionTemplateRepository;
import com.ii.app.repositories.UserRepository;
import com.ii.app.services.interfaces.TransactionTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionTemplateServiceImpl implements TransactionTemplateService {
    private final TransactionTemplateRepository transactionTemplateRepository;

    private final TransactionTemplateMapper transactionTemplateMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public TransactionTemplateServiceImpl(TransactionTemplateMapper transactionTemplateMapper,
                                          TransactionTemplateRepository transactionTemplateRepository) {
        this.transactionTemplateMapper = transactionTemplateMapper;
        this.transactionTemplateRepository = transactionTemplateRepository;
    }

    @Override
    public TransactionTemplateOut create(TransactionTemplateIn transactionTemplateIn) {
        TransactionTemplate mapped = transactionTemplateMapper.DTOtoEntity(transactionTemplateIn);
        Instant currentTime = Instant.now();
        mapped.setCreateDate(currentTime);
        mapped.setModificationDate(currentTime);
        mapped.setUser(userRepository.findByIdentifier(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new RuntimeException("Not found")));
        return transactionTemplateMapper.entityToDTO(transactionTemplateRepository.save(mapped));
    }

    @Override
    public List<TransactionTemplateOut> findAll() {
        return transactionTemplateRepository.findAll()
            .stream()
            .map(transactionTemplateMapper::entityToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public TransactionTemplateOut findOneById(Long id) {
        return transactionTemplateMapper.entityToDTO(
            transactionTemplateRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"))
        );
    }

    @Override
    public TransactionTemplateOut update(Long id, TransactionTemplateIn transactionTemplateIn) {
        TransactionTemplate fromDB = transactionTemplateRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("not found"));

        fromDB.setModificationDate(Instant.now());
        fromDB.setTemplateName(transactionTemplateIn.getTemplateName());
        fromDB.setBalance(transactionTemplateIn.getBalance());
        fromDB.setDestinedAccountNumber(transactionTemplateIn.getDestinedAccountNumber());
        fromDB.setDestinedCurrency(transactionTemplateIn.getDestinedCurrency());
        fromDB.setTitle(transactionTemplateIn.getTitle());
        fromDB.setSourceCurrency(transactionTemplateIn.getSourceCurrency());
        fromDB.setSourceAccountNumber(transactionTemplateIn.getSourceAccountNumber());
        fromDB.setMultiCurrency(transactionTemplateIn.getMultiCurrency());

        return transactionTemplateMapper.entityToDTO(transactionTemplateRepository.save(fromDB));
    }

    @Override
    public void deleteById(Long id) {
        if (!transactionTemplateRepository.existsById(id))
            throw new RuntimeException("Not found");
        transactionTemplateRepository.deleteById(id);
    }

    @Override
    public List<TransactionTemplateOut> findAllByCurrentUser() {
        return transactionTemplateRepository.findAllByUser_Identifier(
            SecurityContextHolder.getContext().getAuthentication().getName()
        ).stream()
            .map(transactionTemplateMapper::entityToDTO)
            .collect(Collectors.toList());
    }
}
