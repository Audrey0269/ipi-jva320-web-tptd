package com.ipi.jva320.controller;

import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ListSalarieController
{
    @Autowired
    private SalarieAideADomicileService salarieAideADomicileService;

    @GetMapping(value="/salaries")
    public String list(final ModelMap model)
    {
        model.put("salaries", salarieAideADomicileService.getSalaries());
        return "list";
    }

    //RECHERCHE PAR NOM
    @GetMapping("/salariés?nom={nom}")
    public String rechercheParNom(final ModelMap model, @Nullable String nom)
    {
        if(salarieAideADomicileService.getSalaries(nom) != )
        {

        }
        else
        {

        }

        return "list";
    }




    //A ajouter dans paramètre après final ModelMap model pour pagination - voir page 28
    //@Requestparam("page") Integer page, @RequestParam("size") Integer size
    //Cela doit donner dans l'url page=2&size=10 par ex
}
