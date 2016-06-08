package in.sdqali.experiments.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class ReposController {
    @RequestMapping(path = "/repos", method = GET)
    @FilterJsonBy(keys = {"fork", "language"})
    public List repos() throws URISyntaxException, IOException {
        URL url = this.getClass().getClassLoader().getResource("repos.json").toURI().toURL();

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(url, List.class);
    }
}
