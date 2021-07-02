package mq.learning.amqp.controller;

import mq.learning.amqp.mq.MsgSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ricky Fung
 */
@RestController
public class DemoController {

    @Autowired
    private MsgSender msgSender;

    @RequestMapping(path = "/demo/send", method = RequestMethod.GET)
    public Map<String, String> send(String msg) {

        Long id = msgSender.send(msg);
        Map<String, String> result = new HashMap<>(4);
        result.put("code", "200");
        result.put("id", id.toString());
        return result;
    }
}
