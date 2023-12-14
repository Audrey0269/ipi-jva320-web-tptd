package com.ipi.jva320.controller;

import com.ipi.jva320.exception.SalarieException;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class DetailSalarieController
{
    @Autowired
    private SalarieAideADomicileService salarieAideADomicileService;

    @GetMapping(value="/salaries/{id}")
    public String detailSalarie(final ModelMap model, @PathVariable Long id)
    {
        model.put("nbrSalarie", "(" + salarieAideADomicileService.countSalaries() + " salarié(s))");
        SalarieAideADomicile salarie = salarieAideADomicileService.getSalarie(id);
        model.put("salarie", salarie);
        return "detail_Salarie";
    }

    //AFFICHER LE DETAIL D'UN SALARIE DE TEST
    //@GetMapping("/salaries/1")
    //public String salarie(final ModelMap model)
    //{
    //    SalarieAideADomicile salarie = new SalarieAideADomicile("Jeannette Dupontelle", LocalDate.of(2021, 7, 1), LocalDate.now(), 0, 0, 10, 1, 0);
    //    model.put("salarie", salarie);
    //    return "detail_Salarie";
    //}


    //CREATION D'UN SALARIE
    @GetMapping("salaries/aide/new")
    public String createSalarie(final ModelMap model)
    {
        SalarieAideADomicile salarie = new SalarieAideADomicile();
        return "/detail_Salarie";
    }

    @PostMapping("salaries/save")
    public String createSalarieBdd(SalarieAideADomicile salarie)
    {
        try
        {
            salarieAideADomicileService.creerSalarieAideADomicile(salarie);
        }
        catch (SalarieException exception)
        {
            //Gestion des erreurs
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erreur lors de la création d'un salarie");
        }
        return "/detail_Salarie";
    }

    //MISE A JOUR D'UN SALARIE
    @PostMapping("salaries/{id}")
    public String updateSalarie(SalarieAideADomicile salarie)
    {
        try
        {
            salarieAideADomicileService.updateSalarieAideADomicile(salarie);
        }
        catch (SalarieException exception)
        {
            //Gestion des erreurs
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erreur lors de la mise à jour d'un salarie");
        }
        return "/detail_Salarie";
    }


    //SUPPRESSION D'UN SALARIE
    @GetMapping(value="/salaries/{id}/delete")
    public String deleteSalarie(final ModelMap model, @PathVariable Long id)
    {
        try
        {
            salarieAideADomicileService.deleteSalarieAideADomicile(id);
            return"redirect:/salaries/";
        }
        catch(SalarieException | EntityExistsException exception)
        {
            //Gestion des erreurs
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erreur lors de la suppression d'un salarie");
        }
    }


}
