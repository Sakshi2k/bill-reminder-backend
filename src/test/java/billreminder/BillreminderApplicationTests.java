package billreminder;

import java.util.Arrays;

import billreminder.model.Bill;
import billreminder.resource.BillResource;
import billreminder.service.BillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BillResource.class)
@WithMockUser

public class BillreminderApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BillService billService;           //

    Long testId = 115L;

    Bill mockBill = new Bill(testId, "Electricity", "June bill", false,
            false,"b6127dfe-39f4-44ae-8dff-8f86d830ca6e", "08/07/2021","other", 1234L);

    String exampleBillJson = "{\n" +
            "    \"id\": 98,\n" +
            "    \"billName\": \"Water Test\",\n" +
            "    \"billDesc\": \"June bill\",\n" +
            "    \"amount\": 1234,\n" +
            "    \"paid\": false,\n" +
            "    \"pastDue\": false,\n" +
            "    \"dueDate\": \"08/07/2021\",\n" +
            "    \"billCode\": \"b6127dfe-39f4-44ae-8dff-8f86d830ca6e\",\n" +
            "    \"billType\": \"water\"\n" +
            "}";

    @Test
    public void retrieveBillDetailsByIdTest() throws Exception {
        // Mocking the bill service
        Mockito.when(billService.findBillById(testId))
                .thenReturn(mockBill);

        // building a request
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/bill/find/"+testId)
                .accept(MediaType.APPLICATION_JSON);

        // Firing the request
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println("Results : " + result.getResponse());
        String expected = "{\n" +
                "    \"id\": 115,\n" +
                "    \"billName\": \"Electricity\",\n" +
                "    \"billDesc\": \"June bill\",\n" +
                "    \"amount\": 1234,\n" +
                "    \"paid\": false,\n" +
                "    \"pastDue\": false,\n" +
                "    \"dueDate\": \"08/07/2021\",\n" +
                "    \"billCode\": \"b6127dfe-39f4-44ae-8dff-8f86d830ca6e\",\n" +
                "    \"billType\": \"other\"\n" +
                "}";

        JSONAssert.assertEquals(expected.toString(), result.getResponse().getContentAsString(), false);
    }

   // Bill mockBill = new Bill(testId, "Electricity", "June bill", false, false,"b6127dfe-39f4-44ae-8dff-8f86d830ca6e", "08/07/2021","other", 1234L);

    @Test
    public void updateBillDetailsTest() throws Exception {
        // Mocking the bill service
        Mockito.when(billService.updateBillById(mockBill))
                .thenReturn(mockBill);

        // building a request
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/bill/update")
                .content(exampleBillJson)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        // Firing the request
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println("Results : " + result.getResponse());
        String expected = "{\n" +
                "    \"id\": 98,\n" +
                "    \"billName\": \"Water Test\",\n" +
                "    \"billDesc\": \"June bill\",\n" +
                "    \"amount\": 1234,\n" +
                "    \"paid\": false,\n" +
                "    \"pastDue\": false,\n" +
                "    \"dueDate\": \"08/07/2021\",\n" +
                "    \"billCode\": \"b6127dfe-39f4-44ae-8dff-8f86d830ca6e\",\n" +
                "    \"billType\": \"water\"\n" +
                "}";

        //JSONAssert.assertEquals(expected.toString(), result.getResponse().getContentAsString(), false);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

}
