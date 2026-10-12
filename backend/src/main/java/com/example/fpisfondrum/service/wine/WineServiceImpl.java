package com.example.fpisfondrum.service.wine;

import com.example.fpisfondrum.exception.NotFoundException;
import com.example.fpisfondrum.model.Wine;
import com.example.fpisfondrum.repo.WineRepo;
import com.example.fpisfondrum.repo.specification.WineSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WineServiceImpl implements WineService {

    private final WineRepo wineRepo;

    @Override
    public List<Wine> findAll() {
        return wineRepo.findAll();
    }

    @Override
    public Wine findById(Long id) {
        return wineRepo.findById(id).orElseThrow(() -> new NotFoundException("Wine not found with id: " + id));
    }

    @Override
    public void save(Wine wine) {
        wineRepo.save(wine);
    }


    @Override
    public Page<Wine> searchWines(List<Long> wineVarietyIds, List<Long> wineStyleIds, Pageable pageable) {

        Specification<Wine> spec = Specification.where(null);

        if (wineVarietyIds != null && !wineVarietyIds.isEmpty()) {
            spec = spec.and(WineSpecification.hasVarietyIn(wineVarietyIds));
        }

        if (wineStyleIds != null && !wineStyleIds.isEmpty()) {
            spec = spec.and(WineSpecification.hasStyleIn(wineStyleIds));
        }

        return wineRepo.findAll(spec, pageable);
    }
}
