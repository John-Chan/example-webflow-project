package io.hosuaby.webflow.controllers;

import io.hosuaby.webflow.domain.Teapot;
import io.hosuaby.webflow.repositories.TeapotRepository;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for CRUD operations with teapots.
 */
@Controller
@RequestMapping("/")
public class TeapotController {

    /** Default teapots to fill repository */
    private static final Set<Teapot> TEAPOTS = new HashSet<Teapot>() {
        private static final long serialVersionUID = 1L;
        {
            add(new Teapot("mouse", "Mouse", "Tefal", Teapot.L0_3));
            add(new Teapot("einstein", "Einstein", "Sony", Teapot.L3));
            add(new Teapot("nemezis", "Nemezis", "Philips", Teapot.L10));
        }
    };

    /** Valid volumes of teapots */
    private static final float[] VOLUMES = { Teapot.L0_3, Teapot.L0_5,
        Teapot.L1, Teapot.L1_5, Teapot.L3, Teapot.L5, Teapot.L8, Teapot.L10 };

    /** Teapot repository */
    @Autowired
    private TeapotRepository repository;

    /**
     * Shows the list of existing teapots.
     *
     * @param model    model
     * @return view name
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showTeapotList(Model model) {
        model.addAttribute("teapots", repository.findAll());
        return "index";
    }

    /**
     * Shows information about one teapot.
     *
     * @param id       teapot id
     * @param model    model
     * @return view name
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET)
    public String showTeapot(
            @PathVariable String id,
            Model model) {
        model.addAttribute("teapot", repository.findOne(id));
        return "show";
    }

    /**
     * Shows creation form for a new teapot.
     *
     * @param model    model
     * @return view name
     */
    @RequestMapping(
            value = "/new",
            method = RequestMethod.GET)
    public String newTeapot(Model model) {
        model.addAttribute("volumes", VOLUMES);
        return "new";
    }

    /**
     * Creates teapot and redirects to the show page of the teapot.
     *
     * @return redirect directive
     */
    @RequestMapping(
            value = "/",
            method = RequestMethod.POST)
    public String createTeapot(
            @ModelAttribute("teapot") Teapot teapot,
            Model model) {
        repository.save(teapot);
        model.addAttribute("teapot", teapot);
        return "redirect:/" + teapot.getId();
    }

    /**
     * Shows edit form of an teapot.
     *
     * @param id       teapot id
     * @param model    model
     * @return view name
     */
    @RequestMapping(
            value = "/{id}/edit",
            method = RequestMethod.GET)
    public String editTeapot(
            @PathVariable String id,
            Model model) {
        model.addAttribute("teapot", repository.findOne(id));
        model.addAttribute("volumes", VOLUMES);
        return "edit";
    }

    /**
     * Updates teapot and redirects to the show page of the teapot.
     *
     * @return redirect directive
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT)
    public String updateTeapot(
            @PathVariable String id,
            @ModelAttribute("teapot") Teapot teapot,
            Model model) {
        repository.save(teapot);
        model.addAttribute("teapot", teapot);
        return "redirect:/" + id;
    }

    /**
     * Deletes an teapot by it's id.
     *
     * @param id    teapot id
     * @return redirect directive
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE)
    public String deleteTeapot(@PathVariable String id) {
        repository.delete(id);
        return "redirect:/";
    }

    /**
     * Resets the teapot repository into initial state.
     */
    @RequestMapping(
            value = "/reset",
            method = RequestMethod.POST)
    @PostConstruct
    public synchronized String reset() {
        repository.deleteAll();
        for (Teapot teapot : TEAPOTS) {
            repository.save(teapot);
        }
        return "redirect:/";
    }

    /**
     * @return valid teapot volumes. Used by flow.
     */
    public float[] getVolumes() {
        return VOLUMES;
    }

}
