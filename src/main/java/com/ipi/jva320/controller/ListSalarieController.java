package com.ipi.jva320.controller;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

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
    @GetMapping("/salariés")
    public String rechercheParNom(final ModelMap model, @Nullable @RequestParam("nom") String nom)
    {
            List<SalarieAideADomicile> listNom = salarieAideADomicileService.getSalaries(nom);

            if(nom != null && !nom.isEmpty())
            {
                if(listNom.isEmpty())
                {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le nom n'a pas été trouvé !");
                }

                model.put("salaries", listNom);
                return "list";
            }
            else
            {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vous devez rentrer un nom !");
            }
    }




    //A ajouter dans paramètre après final ModelMap model pour pagination - voir page 28
    //@Requestparam("page") Integer page, @RequestParam("size") Integer size
    //Cela doit donner dans l'url page=2&size=10 par ex
}
