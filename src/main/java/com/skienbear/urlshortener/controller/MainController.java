package com.skienbear.urlshortener.controller;

import com.google.common.hash.Hashing;
import com.skienbear.urlshortener.domain.Unique;
import com.skienbear.urlshortener.domain.Urls;
import com.skienbear.urlshortener.domain.User;
import com.skienbear.urlshortener.repos.UniqueRepo;
import com.skienbear.urlshortener.repos.UrlsRepo;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.time.ZonedDateTime;

//@RequestMapping("/")
@Controller
public class MainController {

    @Autowired
    private UrlsRepo urlRepo;
    @Autowired
    private UniqueRepo uniqueRepo;

    @RequestMapping("/")
    public String greeting(Model model) {
        Iterable<Urls> urlsAll = urlRepo.findAll();
        Iterable<Unique> uniqueAll = uniqueRepo.findAll();
        for(Urls element:urlsAll){
            LocalDateTime now=LocalDateTime.now();
            //срок жизни ссылки-2 минуты
            LocalDateTime hourbefore = now.minus ( 2 , ChronoUnit.MINUTES );
            if(element.getCreatedAt().isBefore(hourbefore) && element.getCreatedAt()!=null){
                for (Unique element1:uniqueAll){
                    if(element.getShorturl().equals(element1.getShort_url())&&(element.getAuthor().getId().equals(element1.getUserid()))){
                        uniqueRepo.delete(element1);
                    }
                }
                urlRepo.delete(element);}
        }

        Iterable<Urls> urlsAfterDelete = urlRepo.findAll();
        model.addAttribute("urls", urlsAfterDelete);
        return "main";
    }

    @GetMapping("redirect/{id}")
    public Object getUrl(
            @AuthenticationPrincipal User user,
            @PathVariable String id){
        List<Urls> urls = urlRepo.findByShorturl(id);
        List<Unique> uniquelist = uniqueRepo.findByUserid(user.getId());

        if (urls != null && !urls.isEmpty()) {
            HttpHeaders headers=new HttpHeaders();
            int counter=0;
            for(Urls element:urls){
                if(counter>0)break;
                headers.add("Location",element.getLong_url());
                if(!uniquelist.isEmpty()) {
                    element.setRedirects(element.getRedirects() + 1);
                    //если в двух таблицах id пользователей при совпадении коротких ссылок или наоборот, тогда создаем новое уникальное посещение
                    for (Unique element1 : uniquelist) {
                        if (!element1.getUserid().equals(user.getId())&&!element1.getShort_url().equals(id)) {
                            Unique newunique = new Unique(id, user.getId());
                            element.setUnique(element.getUnique() + 1);
                            uniqueRepo.save(newunique);
                        }
                    }
                }else {
                    //если в uniqueTable пустая, то добавляем новую строку
                    element.setRedirects(element.getRedirects() + 1);
                    Unique newunique = new Unique(id, user.getId());
                    element.setUnique(element.getUnique() + 1);
                    uniqueRepo.save(newunique);
                }

                urlRepo.save(element);

                System.out.println("Redirect To: "+element.getLong_url());
                counter++;
            }
            return new ResponseEntity<String>(headers, HttpStatus.FOUND);
        }else{

            return ResponseEntity.notFound().build();
        }

    }


    //удаление данных о короткой ссылке из двух таблиц(urls и uniqueTable)
    @GetMapping("delete/{shorturl}")
    public Object deleteUrl(@PathVariable String shorturl)throws IOException {
        List<Urls> urls = urlRepo.findByShorturl(shorturl);
        List<Unique> uniquelist = uniqueRepo.findByShorturl(shorturl);
        if(urls != null && !urls.isEmpty()) {
            for(Urls element:urls){
                urlRepo.delete(element);
                System.out.println("URL Deleted: " + shorturl);
            }
            for(Unique element1:uniquelist){
                uniqueRepo.delete(element1);
                System.out.println("URL Deleted from unique_table: " + shorturl);
            }
        }
        else{
            System.out.println("Short URL not found : " + shorturl);
        }
        return "redirect:/";
    }


    @PostMapping("/")
    public Object create(
            @AuthenticationPrincipal User user,
            @RequestParam String longurl
    )throws IOException {
        UrlValidator urlValidator = new UrlValidator(new String[]{"http","https"});
        String id;
        List<Urls> exists = urlRepo.findByLongurl(longurl);
        if(urlValidator.isValid(longurl)){
            if(exists.isEmpty()) {
                id = Hashing.murmur3_32().hashString(longurl, StandardCharsets.UTF_8).toString();
                LocalDateTime now = LocalDateTime.now();
                Urls urls = new Urls(id, longurl, user, now);
                urlRepo.save(urls);
                System.out.println("URL Retrieved: " + id);
                return "redirect:/";
            }
            else {
                System.out.println("URL:" +longurl+ " just exist!");
                return "redirect:/";
            }
        }
        throw  new RuntimeException("URL Invalid:"+ longurl);

    }





}
