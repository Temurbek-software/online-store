package online.market.admin.controller;

import lombok.RequiredArgsConstructor;
import online.market.admin.utils.ContractWrapper;
import online.market.model.entity.Contracts;
import online.market.model.entity.Product;
import online.market.service.entity.AuthorService;
import online.market.service.entity.ContractsService;
import online.market.service.entity.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/contract")
public class ContractController {
   private final AuthorService authorService;
   private final ProductService productService;
   private final ContractsService contractsService;

    @GetMapping("/list")
    public String advertisementList(Model model) {
        model.addAttribute("contractList", contractsService.contractList());
        return "model/contract/contractList";
    }
    @GetMapping("/delete/{id}")
    public String deleteByIdAds(@PathVariable("id") Long id, Model model) {
        contractsService.deleteContract(id);
        model.addAttribute("msg", true);
        return "redirect:/contract/list?deleted";
    }
    @GetMapping("/add")
    public String addNewAdv(@ModelAttribute("contract") ContractWrapper contracts, Model model) {
        model.addAttribute("productList", productService.productDtoList(false));
        return "model/contract/addContracts";
    }

    @PostMapping("/save")
    public String insertNewItems(@ModelAttribute("advertisement") ContractWrapper contracts,
                                 Model model, BindingResult result) {
        model.addAttribute("productList", productService.productDtoList(false));
        if (result.hasErrors()) {
            return "model/contract/addContracts";
        }
        Contracts contracts1= new Contracts();
        List<Product> productDTOSet = new ArrayList<>();
        for (String s : contracts.getProductDTOSet()) {
            long authorId=Long.parseLong(s);
            productDTOSet.add(productService.getOneProductDto(authorId));
        }
        if (contracts.getId() != null) {
            contracts1.setId(contracts.getId());
        }
        contracts1.setDate_contact(contracts.getDate_contact());
        contracts1.setImage_posted1(contracts.getImage_posted1());
        contracts1.setAuthorContracts(contracts.getAuthor());
        contracts1.setImageData(contracts.getImageData());
        contracts1.setNotes(contracts.getNotes());
        contracts1.setOutcome(contracts.getOutcome());
        contracts1.setProductSet(productDTOSet);
        contractsService.createNewContract(contracts1);
        return "redirect:/contract/list?success";
    }

}
