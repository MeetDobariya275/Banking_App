package dto;

import org.bson.Document;
import java.util.Map;

public class CurrencyConversionDto extends BaseDto {
    private Map<String, String> conversionValues;

    public CurrencyConversionDto(Map<String, String> conversionValues) {
        this.conversionValues = conversionValues;
    }

    public Map<String, String> getConversionValues() {
        return conversionValues;
    }

    public void setConversionValues(Map<String, String> conversionValues) {
        this.conversionValues = conversionValues;
    }

    @Override
    public Document toDocument() {
        Document document = new Document();
        if (conversionValues != null) {
            document.append("conversionValues", conversionValues);
        }
        return document;
    }
}
