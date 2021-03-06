package es.um.asio.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import es.um.asio.service.filter.LanguageFilter;
import es.um.asio.service.filter.SearchCriteria;
import es.um.asio.service.filter.SearchOperation;
import es.um.asio.service.util.Utils;
import es.um.asio.service.util.ValidationConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Locale;


@Entity
@Table(name = Language.TABLE)
@Getter
@ToString(includeFieldNames = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Language {

    @Transient
    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(Language.class);
    /**
     * Version ID.
     */
    private static final long serialVersionUID = -8605786237765754616L;

    /**
     * The id.
     */
    @Id
    @Column(name = Columns.ISO, columnDefinition = "VARCHAR(20)", length = 20)
    @EqualsAndHashCode.Include
    @ApiModelProperty(	example="ES",allowEmptyValue = false, position=1, accessMode = ApiModelProperty.AccessMode.READ_WRITE, value = "Required: ISO Code of Country", required = false)
    private String iso;

    /**
     * LANGUAGE.
     */
    @ApiModelProperty(	example="ES",allowEmptyValue = false, position=2, accessMode = ApiModelProperty.AccessMode.READ_WRITE, value = "Optional: Name of language. ", required = false)
    @Size(min = 1, max = ValidationConstants.MAX_LENGTH_DEFAULT)
    @Column(name = Columns.LANGUAGE, nullable = true,columnDefinition = "VARCHAR(20)",length = 20)
    private String languageStr;

    /**
     * REGION.
     */
    @ApiModelProperty(	example="ES",allowEmptyValue = false, position=2, accessMode = ApiModelProperty.AccessMode.READ_WRITE, value = "Optional: Name of region. ", required = false)
    @Size(min = 1, max = ValidationConstants.MAX_LENGTH_DEFAULT)
    @Column(name = Columns.REGION, nullable = true,columnDefinition = "VARCHAR(20)",length = 20)
    private String region;

    /**
     * VARIANT.
     */
    @ApiModelProperty(	example="ES",allowEmptyValue = false, position=2, accessMode = ApiModelProperty.AccessMode.READ_WRITE, value = "Optional: Name of variant. ", required = false)
    @Size(min = 1, max = ValidationConstants.MAX_LENGTH_DEFAULT)
    @Column(name = Columns.VARIANT, nullable = true,columnDefinition = "VARCHAR(20)",length = 20)
    private String variant;

    /**
     * VARIANT.
     */
    @ApiModelProperty(	example="ES",allowEmptyValue = false, position=2, accessMode = ApiModelProperty.AccessMode.READ_WRITE, value = "Optional: Name of Script. ", required = false)
    @Size(min = 1, max = ValidationConstants.MAX_LENGTH_DEFAULT)
    @Column(name = Columns.SCRIPT, nullable = true,columnDefinition = "VARCHAR(20)",length = 20)
    private String script;

    /**
     * NAME.
     */
    @ApiModelProperty(	example="Spain",allowEmptyValue = false, position=2, accessMode = ApiModelProperty.AccessMode.READ_WRITE, value = "Optional: Name of country. ", required = false)
    @Size(min = 1, max = ValidationConstants.MAX_LENGTH_DEFAULT)
    @Column(name = Columns.NAME, nullable = true,columnDefinition = "VARCHAR(100)",length = 100)
    private String name;

    /**
     * DOMAIN NAME.
     */
    @ApiModelProperty(	example="dominio",allowEmptyValue = false, position=3, accessMode = ApiModelProperty.AccessMode.READ_WRITE, value = "Required: Name of Domain in selected language. ", required = false)
    @Size(min = 1, max = ValidationConstants.MAX_LENGTH_DEFAULT)
    @Column(name = Columns.DOMAIN, nullable = true,columnDefinition = "VARCHAR(100)",length = 100)
    private String domain;

    /**
     * SUB DOMAIN NAME.
     */
    @ApiModelProperty(	example="sub-dominio",allowEmptyValue = false, position=4, accessMode = ApiModelProperty.AccessMode.READ_WRITE, value = "Required: Name of Sub Domain in selected language. ", required = false)
    @Size(min = 1, max = ValidationConstants.MAX_LENGTH_DEFAULT)
    @Column(name = Columns.SUB_DOMAIN, nullable = true,columnDefinition = "VARCHAR(100)",length = 100)
    private String subDomain;

    /**
     * TYPE NAME.
     */
    @ApiModelProperty(	example="tipo",allowEmptyValue = false, position=5, accessMode = ApiModelProperty.AccessMode.READ_WRITE, value = "Required: Name of type in selected language. ", required = false)
    @Size(min = 1, max = ValidationConstants.MAX_LENGTH_DEFAULT)
    @Column(name = Columns.TYPE, nullable = true,columnDefinition = "VARCHAR(100)",length = 100)
    private String type;

    /**
     * CONCEPT NAME.
     */
    @ApiModelProperty(	example="class",allowEmptyValue = false, position=6, accessMode = ApiModelProperty.AccessMode.READ_WRITE, value = "Required: Name of Concept(class) in selected language. ", required = false)
    @Size(min = 1, max = ValidationConstants.MAX_LENGTH_DEFAULT)
    @Column(name = Columns.CONCEPT, nullable = true,columnDefinition = "VARCHAR(100)",length = 100)
    private String concept;

    /**
     * REFERENCE NAME.
     */
    @ApiModelProperty(	example="item",allowEmptyValue = false, position=7, accessMode = ApiModelProperty.AccessMode.READ_WRITE, value = "Required: Name of Reference(item) in selected language. ", required = false)
    @Size(min = 1, max = ValidationConstants.MAX_LENGTH_DEFAULT)
    @Column(name = Columns.REFERENCE, nullable = true,columnDefinition = "VARCHAR(100)",length = 100)
    private String reference;

    /**
     * Is Default language.
     */
    @ApiModelProperty(	value = "true if is the default language", example="true", allowEmptyValue = false, allowableValues = "true, false",position =8, accessMode = ApiModelProperty.AccessMode.READ_WRITE, required = false)
    @Column(name = Columns.IS_DEFAULT)
    public Boolean isDefault = false;


    public Language() {
    }

    public Language(String iso, String name, String domain, String subDomain, String type, String concept, String reference, boolean isDefault) {
        setIso(iso);
        this.name = name;
        this.domain = domain;
        this.subDomain = subDomain;
        this.type = type;
        this.concept = concept;
        this.reference = reference;
        this.isDefault = isDefault;
    }

    public void setIso(String iso) {
        try {
            Locale l = Locale.forLanguageTag(iso);
            if (Utils.isValidString(l.getDisplayLanguage())) {
                languageStr = l.getDisplayLanguage();
            }
            if (Utils.isValidString(l.getDisplayCountry())) {
                region = l.getDisplayCountry();
            }
            if (Utils.isValidString(l.getDisplayScript())) {
                variant = l.getDisplayVariant();
            }
            if (Utils.isValidString(l.getDisplayScript())) {
                script = l.getDisplayScript();
            }
            this.iso = iso;
        } catch (Exception e) {
            logger.error("Exception: {}",e);
        }
    }



    /**
     * Table name.
     */
    public static final String TABLE = "LANGUAGE";


    /**
     * Column name constants.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    static final class Columns {
        /**
         * ID column.
         */
        protected static final String ISO = "ISO";

        /**
         * LANGUAGE column.
         */
        protected static final String LANGUAGE = "LANGUAGE";

        /**
         * REGION column.
         */
        protected static final String REGION = "REGION";

        /**
         * VARIANT column.
         */
        protected static final String VARIANT = "VARIANT";

        /**
         * SCRIPT column.
         */
        protected static final String SCRIPT = "SCRIPT";

        /**
         * ISO.
         */
        protected static final String NAME = "NAME";

        /**
         * DOMAIN NAME.
         */
        protected static final String DOMAIN = "DOMAIN_NAME";

        /**
         * SUB_DOMAIN NAME.
         */
        protected static final String SUB_DOMAIN = "SUB_DOMAIN_NAME";

        /**
         * TYPE NAME.
         */
        protected static final String TYPE = "TYPE_NAME";

        /**
         * CONCEPT NAME.
         */
        protected static final String CONCEPT = "CONCEPT_NAME";

        /**
         * URI column.
         */
        protected static final String REFERENCE = "REFERENCE_NAME";

        /**
         * URI column.
         */
        protected static final String IS_DEFAULT = "IS_DEFAULT";

    }

    public LanguageFilter buildFilterByEntity() {
        LanguageFilter f = new LanguageFilter();
        if (this.iso != null && !this.iso.equals("")) {
            f.add(new SearchCriteria("iso", this.iso, SearchOperation.EQUAL));
        }
        if (this.name != null && !this.name.equals("")) {
            f.add(new SearchCriteria("name", this.name, SearchOperation.EQUAL));
        }
        if (this.domain != null && !this.domain.equals("")) {
            f.add(new SearchCriteria("domain", this.domain, SearchOperation.EQUAL));
        }
        if (this.subDomain != null && !this.subDomain.equals("")) {
            f.add(new SearchCriteria("subDomain", this.subDomain, SearchOperation.EQUAL));
        }
        if (this.type != null && !this.type.equals("")) {
            f.add(new SearchCriteria("type", this.type, SearchOperation.EQUAL));
        }
        if (this.concept != null && !this.concept.equals("")) {
            f.add(new SearchCriteria("concept", this.concept, SearchOperation.EQUAL));
        }
        if (this.reference != null && !this.reference.equals("")) {
            f.add(new SearchCriteria("reference", this.reference, SearchOperation.EQUAL));
        }
        if (this.isDefault != null) {
            f.add(new SearchCriteria("isDefault", this.isDefault, SearchOperation.EQUAL));
        }
        return f;
    }

    public LanguageFilter buildFilterByEntityUniqueProperties() {
        LanguageFilter f = new LanguageFilter();
        if (this.iso != null && !this.iso.equals("")) {
            f.add(new SearchCriteria("iso", this.iso, SearchOperation.EQUAL));
        }
        return f;
    }

    public void merge(Language other){
        this.name = other.getName();
        this.domain = other.getDomain();
        this.subDomain = other.getSubDomain();
        this.type = other.getType();
        this.concept = other.getConcept();
        this.reference = other.getReference();
        this.isDefault = other.getIsDefault();
    }

    @Override
    public String toString() {
        return "Language{" +
                "ISO='" + iso + '\'' +
                ", language='" + languageStr + '\'' +
                ", region='" + region + '\'' +
                ", variant='" + variant + '\'' +
                ", script='" + script + '\'' +
                ", name='" + name + '\'' +
                ", domain='" + domain + '\'' +
                ", subDomain='" + subDomain + '\'' +
                ", type='" + type + '\'' +
                ", concept='" + concept + '\'' +
                ", reference='" + reference + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}
