package com.ipi.jva320.controller;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.repository.SalarieAideADomicileRepository;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedArray;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class ListSalarieController
{
    @Autowired
    private SalarieAideADomicileService salarieAideADomicileService;
    private SalarieAideADomicileRepository salarieAideADomicileRepository;

    @GetMapping(value="/salaries")
    public String list(final ModelMap model,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "5") int size,
                       @RequestParam(defaultValue = "ASC") String sortDirection,
                       @RequestParam(defaultValue = "nom") String sortProperty)
    {
        //Pagination
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty);
        Page<SalarieAideADomicile> salariesPage = salarieAideADomicileService.getSalaries(pageable);
        model.put("salaries", salariesPage.getContent());
        model.put("currentPage", page);
        model.put("totalPages", salariesPage.getTotalPages());
        model.put("size", size);
        model.put("sortDirection", sortDirection);
        model.put("sortProperty", sortProperty);
        model.put("nbrSalaries",salarieAideADomicileService.countSalaries());

        //Afficher le nombre de salarié dans la nav
        model.put("nbrSalarie", "(" + salarieAideADomicileService.countSalaries() + " salarié(s))");

        //Trie du tableau par nom/id (TEST)
        //Collections.sort(lSalaries, Comparator.comparing(SalarieAideADomicile::getNom));
        //Collections.sort(lSalaries, Comparator.comparing(SalarieAideADomicile::getId));

        //Afficher la page de la liste des salariés
        //List<SalarieAideADomicile> lSalaries = salarieAideADomicileService.getSalaries();
        //model.put("salaries", lSalaries);

        return "/list";
    }


    //RECHERCHE PAR NOM
    @GetMapping("/salariés")
    public String rechercheParNom(final ModelMap model,
                                  @Nullable @RequestParam("nom") String nom,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size,
                                  @RequestParam(defaultValue = "ASC") String sortDirection,
                                  @RequestParam(defaultValue = "nom") String sortProperty)

    {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty);
        List<SalarieAideADomicile> salariesPage = salarieAideADomicileService.getSalaries(nom, pageable);

        int debutIndex = page * size;
        int finIndex = Math.min(debutIndex + size, salariesPage.size());

        List<SalarieAideADomicile> paginatedSalaries = salariesPage.subList(debutIndex, finIndex);

        model.put("salaries", paginatedSalaries);
        model.put("currentPage", page);
        model.put("totalPages", (int) Math.ceil((double) salariesPage.size() / size));
        model.put("size", size);
        model.put("sortDirection", sortDirection);
        model.put("sortProperty", sortProperty);
        model.put("nbrSalaries",salarieAideADomicileService.countSalaries());

            List<SalarieAideADomicile> listNom = salarieAideADomicileService.getSalaries(nom);

            if(nom != null && !nom.isEmpty())
            {
                if(listNom.isEmpty())
                {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le nom n'a pas été trouvé !");
                }

                model.put("salaries", listNom);
                return "/list";
            }
            else
            {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vous devez rentrer un nom !");
            }
    }

}
