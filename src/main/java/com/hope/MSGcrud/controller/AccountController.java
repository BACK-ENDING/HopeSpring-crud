package com.hope.MSGcrud.controller;

import com.hope.MSGcrud.account.Account;
import com.hope.MSGcrud.account.JdbcAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    JdbcAccountRepository jdbcAccountRepository;

    @GetMapping("/list") // 게시글 목록
    public String list(Model model){

        model.addAttribute("accounts", jdbcAccountRepository.findAll());
        model.addAttribute("count" , jdbcAccountRepository.count());

        return "account/list";
    }

    @GetMapping("/add") // 게시글 추가 Form
    public String add() {
        return "account/add";
    }

    @PostMapping("/add") //추가 처리
    public String addProcess(String email){
        jdbcAccountRepository.save(new Account(email));

        return "redirect:/account/list";
    }

    @GetMapping("/{id}") //상세보기
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("account", jdbcAccountRepository.findById(id));

        return "account/view";
    }

    @GetMapping("/{id}/modify") //수정 Form
    public String modify(@PathVariable Long id, Model model) {

        model.addAttribute("account", jdbcAccountRepository.findById(id));

        return "account/modify";
    }

    @PostMapping("/{id}/modify") //수정 처리
    public String modifyProcess(@PathVariable Long id, String email) {
        Account account = jdbcAccountRepository.findById(id);
        account.setEmail(email);
        jdbcAccountRepository.update(account);

        return String.format("redirect:/account/%d", id) ;
    }

    @PostMapping("/{id}/delete") //삭제 처리
    public String deleteProcess(@PathVariable Long id) {
        jdbcAccountRepository.deleteById(id);

        return "redirect:/account/list";
    }

}
