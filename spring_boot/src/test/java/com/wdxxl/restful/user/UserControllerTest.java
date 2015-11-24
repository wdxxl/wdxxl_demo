package com.wdxxl.restful.user;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class UserControllerTest {
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new UserRestController()).build();
    }

    @Test
    public void testGetUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/{user}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.is("{\"id\":1,\"name\":\"World\"}")));
    }

    @Test
    public void testGetUserWithParam() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/{user}?name=king", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.is("{\"id\":1,\"name\":\"king\"}")));
    }

    @Test
    public void testGetUserCustomers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/{user}/customers", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.is(
                        "[{\"id\":1,\"name\":\"jake\"},{\"id\":2,\"name\":\"kingson\"},{\"id\":3,\"name\":\"james\"}]")));
    }

    @Test
    public void testCreateUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/users/createUser")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"name\":\"king create\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.is("{\"id\":1,\"name\":\"king create\"}")));
    }

    @Test
    public void testModifyUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/users/modifyUser")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"name\":\"king modify\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.is("{\"id\":1,\"name\":\"king modify\"}")));
    }

    @Test
    public void testPatchUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.patch("/users/patchUser")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"name\":\"king patch\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.is("{\"id\":1,\"name\":\"king patch\"}")));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/users/{user}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.is("true")));
    }

}
