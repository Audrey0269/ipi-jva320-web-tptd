package com.ipi.jva320.controller;

import com.ipi.jva320.exception.SalarieException;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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


    //CREATION SALARIE
    @GetMapping("salaries/aide/new")
    public String createSalarie(final ModelMap model)
    {
        SalarieAideADomicile salarie = new SalarieAideADomicile();
        model.put("salarie", salarie);
        model.put("actionUrl", "/salaries/save");
        return "detail_Salarie";
    }

    @PostMapping("salaries/save")
    public String createSalarieBdd(@ModelAttribute SalarieAideADomicile salarie)
    {
        try
        {
            salarieAideADomicileService.creerSalarieAideADomicile(salarie);
            return "redirect:/salaries/" + salarie.getId();
        }
        catch (SalarieException | EntityExistsException exception)
        {
            return "redirect:/salaries/form?error=true";
        }
    }



    //MISE A JOUR SALARIE
    /*@GetMapping("salaries/1")
    public String updateSalarie(final ModelMap model, @PathVariable Long id, SalarieAideADomicile salarieAideADomicile)
    {
        SalarieAideADomicile salarie = salarieAideADomicileService.getSalarie(id);
        model.put("salarie", salarie);
        model.put("actionUrl", "/salarie/1");  //Add chemin (1 pour create et 1 pour update)
        //return "detail_Salarie";
        return "redirect:/salaries/" + salarieAideADomicile.getId();
    }

    @PostMapping("salaries/1")
    public SalarieAideADomicile updateSalarieBdd(SalarieAideADomicile salarieAideADomicile)
    {

        //model.put("salarie", salarie);
        //model.put("actionUrl", "/salarie/save");

        //Redirection vers HTTP GET /salarie/1
        return salarieAideADomicileService.updateSalarieAideADomicile(salarieAideADomicile);
    }
    */

    /*
    //DELETE SALARIE
    @GetMapping(value="/salaries/1")
    public String deleteSalarie(final ModelMap model, @PathVariable Long id)
    {
        model.put("actionUrl", "/salaries/1/delete");

        try
        {
            salarieAideADomicileService.deleteSalarieAideADomicile(id);
            return"redirect:/salaries/";
        }
        catch(SalarieException | EntityExistsException exception)
        {
            //Gestion des erreurs
            return "redirect:/salaries/detail/" + id + "?error=true";
        }

    }
    */
}
