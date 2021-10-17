package pw.ewen.WLPT.controllers.resources.yule;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domains.DTOs.resources.yule.YuleResourceGwWcDTO;
import pw.ewen.WLPT.domains.dtoconvertors.resources.yule.YuleResourceGwWcDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceGwWc;
import pw.ewen.WLPT.services.resources.yule.YuleResourceBaseService;
import pw.ewen.WLPT.services.resources.yule.YuleResourceGwWcService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * created by wenliang on 2021/10/12
 */
@RestController
@RequestMapping(value = "/resources/yules/wcs")
public class YuleGwWcController {
    private final YuleResourceGwWcDTOConvertor yuleResourceGwWcDTOConvertor;
    private final YuleResourceGwWcService yuleResourceGwWcService;
    private final YuleResourceBaseService yuleResourceBaseService;

    public YuleGwWcController(YuleResourceGwWcDTOConvertor yuleResourceGwWcDTOConvertor, YuleResourceGwWcService yuleResourceGwWcService, YuleResourceBaseService yuleResourceBaseService) {
        this.yuleResourceGwWcDTOConvertor = yuleResourceGwWcDTOConvertor;
        this.yuleResourceGwWcService = yuleResourceGwWcService;
        this.yuleResourceBaseService = yuleResourceBaseService;
    }

    @PutMapping
    public YuleResourceGwWcDTO save(@RequestBody YuleResourceGwWcDTO wcDTO) {
        YuleResourceGwWc wc = yuleResourceGwWcDTOConvertor.toWc(wcDTO);
        return yuleResourceGwWcDTOConvertor.toDTO(this.yuleResourceGwWcService.save(wc));
    }

    @GetMapping(value = "/byResourceId/{resourceId}")
    public List<YuleResourceGwWcDTO> getByResourceId(@PathVariable long resourceId) {
        List<YuleResourceGwWcDTO> wcDTOs = new ArrayList<>();
        List<YuleResourceGwWc> wcs = this.yuleResourceBaseService.findWcsByResourceId(resourceId);
        wcs.forEach(wc -> {
            YuleResourceGwWcDTO wcDTO = yuleResourceGwWcDTOConvertor.toDTO(wc);
            wcDTOs.add(wcDTO);
        });
        return wcDTOs;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<YuleResourceGwWcDTO> getOne(@PathVariable long id){
        return this.yuleResourceGwWcService.findOne(id).map(wc -> {
            YuleResourceGwWcDTO wcDTO = yuleResourceGwWcDTOConvertor.toDTO(wc);
            return new ResponseEntity<>(wcDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{ids}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "ids") String ids) {
        List<String> idsList = Arrays.asList(ids.split(","));
        idsList.forEach( (id) -> {
            long longId = Long.parseLong(id);
            try {
                yuleResourceGwWcService.findOne(longId).ifPresent(yuleResourceGwWcService::delete);
            } catch (NumberFormatException ignored) {}
        });
    }
}
