package cool.blink.back.utilities;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvUtilities {

    public static final synchronized String serialize(final Object object, final Class type, final Boolean withHeaders) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.registerModule(new JodaModule());
        CsvSchema csvSchema;
        if (withHeaders) {
            csvSchema = csvMapper.schemaFor(type).withHeader();
        } else {
            csvSchema = csvMapper.schemaFor(type).withoutHeader();
        }
        return csvMapper.writer(csvSchema).writeValueAsString(object);
    }

    public static final synchronized List<Object> deserialize(final String csv, final Class type, final Boolean hasHeaders) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.registerModule(new JodaModule());
        CsvSchema csvSchema;
        if (hasHeaders) {
            csvSchema = csvMapper.schemaFor(type).withHeader();
        } else {
            csvSchema = csvMapper.schemaFor(type).withoutHeader();
        }
        MappingIterator<Object> mappingIterator = csvMapper.readerFor(type).with(csvSchema).readValues(csv);
        List<Object> objects = new ArrayList<>();
        while (mappingIterator.hasNext()) {
            objects.add(mappingIterator.next());
        }
        return objects;
    }

}
