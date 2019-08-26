package sinagajunior.convert.utility;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @project raisa
 * @Author royantonius on 01/08/19 .
 */


public class PusdafilConverter {

    public static List<Object> ConvertCSVToJson(File input){
        CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
        csvSchema.skipsFirstDataRow();
        CsvMapper csvMapper = new CsvMapper();
        // Read data from CSV file
        try {
            List<Object> readAll = csvMapper.readerFor(Map.class).with(csvSchema).readValues(input).readAll();
             return  readAll;
           // ObjectMapper mapper = new ObjectMapper();
           // return  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(readAll);
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }



//    public static void main(String[] args) throws Exception {
//     //System.out.println("test convert : " + PusdafilConverter.ConvertCSVToJson());
//    }








}
