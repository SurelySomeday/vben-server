package top.yxlgx.wink.admin.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import top.yxlgx.wink.common.jpa.dto.BaseQuery;
import top.yxlgx.wink.admin.service.DeptService;
import top.yxlgx.wink.common.security.client.endpoint.SsoClientController;

import java.nio.charset.StandardCharsets;


/**
 * @author yanxin
 * @description
 */
/*@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT) // (1)
@AutoConfigureMockMvc*/
@WebMvcTest(controllers = DeptController.class,excludeAutoConfiguration = {SsoClientController.class})
class DeptControllerTest {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    DeptService deptService;

    //导入依赖
/*    @Configuration
    @Import({ ToolConfig.class})
    public static class AppConfig {
    }*/



    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void list() throws Exception {
        Mockito.when(deptService.findAll(Mockito.any(BaseQuery.class),Mockito.any()))
                .thenReturn(Page.empty());
        mockMvc.perform(
                                MockMvcRequestBuilders.get("/dept")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}