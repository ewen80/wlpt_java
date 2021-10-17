package pw.ewen.WLPT.controllers.resources.yule;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domains.DTOs.resources.yule.YuleResourceGwRoomDTO;
import pw.ewen.WLPT.domains.dtoconvertors.resources.yule.YuleResourceGwRoomDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceGwRoom;
import pw.ewen.WLPT.services.resources.yule.YuleResourceBaseService;
import pw.ewen.WLPT.services.resources.yule.YuleResourceGwRoomService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * created by wenliang on 2021/10/10
 */
@RestController
@RequestMapping(value = "/resources/yules/rooms")
public class YuleGwRoomController {
    private final YuleResourceGwRoomService yuleResourceGwRoomService;
    private final YuleResourceBaseService yuleResourceBaseService;
    private final YuleResourceGwRoomDTOConvertor yuleResourceGwRoomDTOConvertor;

    public YuleGwRoomController(YuleResourceGwRoomService yuleResourceGwRoomService, YuleResourceBaseService yuleResourceBaseService, YuleResourceGwRoomDTOConvertor yuleResourceGwRoomDTOConvertor) {
        this.yuleResourceGwRoomService = yuleResourceGwRoomService;
        this.yuleResourceBaseService = yuleResourceBaseService;
        this.yuleResourceGwRoomDTOConvertor = yuleResourceGwRoomDTOConvertor;
    }

    @PutMapping
    public YuleResourceGwRoomDTO save(@RequestBody YuleResourceGwRoomDTO roomDTO) {
        YuleResourceGwRoom room = yuleResourceGwRoomDTOConvertor.toRoom(roomDTO);
        return yuleResourceGwRoomDTOConvertor.toDTO(this.yuleResourceGwRoomService.save(room));
    }

    @GetMapping(value = "/byResourceId/{resourceId}")
    public List<YuleResourceGwRoomDTO> getByResourceId(@PathVariable long resourceId) {
        List<YuleResourceGwRoomDTO> roomDTOs = new ArrayList<>();
        List<YuleResourceGwRoom> rooms = this.yuleResourceBaseService.findRoomsByResourceId(resourceId);
        rooms.forEach(room -> {
            YuleResourceGwRoomDTO roomDTO = yuleResourceGwRoomDTOConvertor.toDTO(room);
            roomDTOs.add(roomDTO);
        });
        return roomDTOs;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<YuleResourceGwRoomDTO> getOne(@PathVariable long id){
        return this.yuleResourceGwRoomService.findOne(id).map(room -> {
            YuleResourceGwRoomDTO roomDTO = yuleResourceGwRoomDTOConvertor.toDTO(room);
            return new ResponseEntity<>(roomDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{ids}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "ids") String ids) {
        List<String> idsList = Arrays.asList(ids.split(","));
        idsList.forEach( (id) -> {
            long longId = Long.parseLong(id);
            try {
                yuleResourceGwRoomService.findOne(longId).ifPresent(yuleResourceGwRoomService::delete);
            } catch (NumberFormatException ignored) {}
        });
    }
}
