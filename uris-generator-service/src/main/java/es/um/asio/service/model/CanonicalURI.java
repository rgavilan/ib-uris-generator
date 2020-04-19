package es.um.asio.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import es.um.asio.service.filter.CanonicalURIFilter;
import es.um.asio.service.filter.SearchCriteria;
import es.um.asio.service.filter.SearchOperation;
import es.um.asio.service.filter.URIMapFilter;
import es.um.asio.service.util.Utils;
import es.um.asio.service.util.ValidationConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = CanonicalURI.TABLE)
@Getter
@ToString(includeFieldNames = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CanonicalURI implements Serializable {

    /**
     * Version ID.
     */
    private static final long serialVersionUID = -8605786237765754616L;

    /**
     * The id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = Columns.ID)
    @EqualsAndHashCode.Include
    @ApiModelProperty(hidden = true)
    private long id;

    /**
     * DOMAIN.
     */
    @ApiModelProperty(	example="hercules",allowEmptyValue = false, position =1, readOnly=false, value = "Required: Domain represents the highest level of the namespace for URI resolution, and for providing relevant information about the owner of the information. ", required = true)
    @Size(min = 1, max = ValidationConstants.MAX_LENGTH_DEFAULT)
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    @Column(name = Columns.DOMAIN, nullable = false,columnDefinition = "VARCHAR(100)",length = 100)
    private String domain;

    /**
     * SUB_DOMAIN.
     */
    @ApiModelProperty(	example="um", allowEmptyValue = true,position =2, readOnly=false, value = "Optional: \n" +
            "It provides information about the entity or department within the entity to which the information resource belongs. Represents the lowest level of the namespace for URI resolution, and for providing relevant information about the owner of the information.", required = true)
    @Size(min = 1, max = ValidationConstants.MAX_LENGTH_DEFAULT)
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    @Column(name = Columns.SUB_DOMAIN, nullable = true,columnDefinition = "VARCHAR(100)",length = 100)
    private String subDomain;

    /**
     * TYPE.
     * Relation Bidirectional ManyToOne
     */

    @ApiModelProperty(	example="ref", allowEmptyValue = false, allowableValues = "cat, def, kos, ref",position =3, readOnly=false, value = "Required: " +
            "Sets the type of information the resource contains. One of this: cat | def | kos | res", required = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Type type;

    /**
     * TYPE.
     * Relation Bidirectional ManyToOne
     */
    @ApiModelProperty(hidden = true)
    @Column(name = Columns.TYPE_ID_CODE, nullable = true,columnDefinition = "VARCHAR(3)",length = 3)
    private String typeIdCode;

    /**
     * CONCEPT.
     */
    @ApiModelProperty(	example="university", allowEmptyValue = true, position =4, readOnly=false, value = "Optional:"+
            "Concepts are abstract representations that correspond to the classes or properties of the vocabularies or ontologies used to semantically represent resources. In addition to the concept, an unambiguous reference to specific instances may be represented. Required only if is a ref type", required = true)
    @Size(min = 1, max = ValidationConstants.MAX_LENGTH_DEFAULT)
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    @Column(name = Columns.CONCEPT, nullable = true,columnDefinition = "VARCHAR(100)",length = 100)
    private String concept;

    /**
     * REFERENCE.
     */
    @ApiModelProperty(	example="12345", allowEmptyValue = true, position =5, readOnly=false, value = "Optional:"+
            "Instances are representations in real world that correspond to the instances of the class defined in concepts. In addition to the concept, an unambiguous reference to specific instances may be represented. Required only if is a ref type, and concept is defined", required = true)
    @Size(min = 1, max = ValidationConstants.MAX_LENGTH_DEFAULT)
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    @Column(name = Columns.REFERENCE, nullable = true,columnDefinition = "VARCHAR(100)",length = 100)
    private String reference;

    /**
     * FULL_URI.
     */
    @ApiModelProperty(	example="12345", allowEmptyValue = true, position =6, readOnly=true, value = "Full URI Result", required = true)
    @Size(min = 1, max = ValidationConstants.MAX_LENGTH_DEFAULT)
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    @Column(name = Columns.FULL_URI, unique = true,nullable = true,columnDefinition = "VARCHAR(400)",length = 400)
    private String fullURI;

    /**
     * Is Entity.
     */
    @ApiModelProperty(	value = "true if is entity", example="true", allowEmptyValue = false, allowableValues = "true, false",position =7, readOnly=false, required = true)
    @Column(name = URIMap.Columns.IS_ENTITY)
    private Boolean isEntity = false;

    /**
     * Is Property.
     */
    @ApiModelProperty(	value = "true if is property", example="true", allowEmptyValue = false, allowableValues = "true, false",position =8, readOnly=false, required = true)
    @Column(name = URIMap.Columns.IS_PROPERTY)
    private Boolean isProperty = false;

    /**
     * Is Instance.
     */
    @ApiModelProperty(	value = "true if is instance", example="true", allowEmptyValue = false, allowableValues = "true, false",position =9, readOnly=false, required = true)
    @Column(name = URIMap.Columns.IS_INSTANCE)
    private Boolean isInstance = false;

    /**
     * Entity Name.
     */
    @ApiModelProperty(	value = "name of entity", example="entity", allowEmptyValue = false,position =10, readOnly=false, required = true)
    @Column(name = Columns.ENTITY_NAME, nullable = false,columnDefinition = "VARCHAR(100)",length = 100)
    private String entityName;

    /**
     * Property Name.
     */
    @ApiModelProperty(	value = "name of property", example="property", allowEmptyValue = true,position =11, readOnly=false, required = false)
    @Column(name = Columns.PROPERTY_NAME, nullable = true,columnDefinition = "VARCHAR(100)",length = 100)
    private String propertyName;

    /**
     * Relation Bidirectional CanonicalURILanguage OneToMany
     */
    @OneToMany(mappedBy = "canonicalURI" , cascade = CascadeType.ALL)
    private Set<CanonicalURILanguage> canonicalURILanguages;

    /**
     * Table name.
     */
    public static final String TABLE = "CANONICAL_URI";

    public CanonicalURI() {
    }

    public CanonicalURI(String domain, String subDomain, Type t, String concept, String reference) {
        this.domain = domain;
        this.subDomain = subDomain;
        setType(t);
        this.concept = concept;
        this.reference = reference;
        generateFullURL();
        updateState();
    }


    public void setDomain(String domain) {
        this.domain = domain;
        generateFullURL();
        updateState();
    }

    public void setSubDomain(String subDomain) {
        this.subDomain = subDomain;
    }

    public void setType(Type type) {
        this.type = type;
        if (type!=null)
            typeIdCode = type.getCode();
    }

    public void setConcept(String concept) {
        this.concept = concept;
        this.entityName = concept;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
        this.reference = propertyName;
    }

    public void setIsEntity(Boolean isEntity) {
        this.isEntity = isEntity;
        this.isInstance = false;
        this.isProperty = false;

    }

    public void setIsProperty(Boolean isProperty) {
        this.isProperty = isProperty;
        this.isEntity = false;
        this.isInstance = false;
    }

    public void setIsInstance(Boolean isInstance) {
        this.isInstance = isInstance;
        this.isEntity = false;
        this.isProperty = false;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    /**
     * Column name constants.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    static final class Columns {
        /**
         * ID column.
         */
        protected static final String ID = "ID";

        /**
         * BASE URL column.
         */
        protected static final String DOMAIN = "DOMAIN";

        /**
         * BASE URL column.
         */
        protected static final String SUB_DOMAIN = "SUB_DOMAIN";

        /**
         * Entity column.
         */
        protected static final String TYPE = "TYPE_CODE";

        /**
         * Entity column.
         */
        protected static final String TYPE_ID_CODE = "TYPE_ID_CODE";

        /**
         * Property column.
         */
        protected static final String CONCEPT = "CONCEPT";

        /**
         * URI column.
         */
        protected static final String REFERENCE = "REFERENCE";

        /**
         * IDENTIFIER column.
         */
        protected static final String FULL_URI = "FULL_URI";

        /**
         * IS_ENTITY column.
         */
        protected static final String IS_ENTITY = "IS_ENTITY";

        /**
         * IS_PROPERTY column.
         */
        protected static final String IS_PROPERTY = "IS_PROPERTY";

        /**
         * IS_Instance column.
         */
        protected static final String IS_INSTANCE = "IS_INSTANCE";

        /**
         * IS_PROPERTY column.
         */
        protected static final String PROPERTY_NAME = "PROPERTY_NAME";

        /**
         * IS_Instance column.
         */
        protected static final String ENTITY_NAME = "ENTITY_NAME";
    }

    public CanonicalURIFilter buildFilterByEntity() {
        CanonicalURIFilter f = new CanonicalURIFilter();
        if (this.id != 0) {
            f.add(new SearchCriteria("id", this.id, SearchOperation.EQUAL));
        }
        if (this.domain != null) {
            f.add(new SearchCriteria("domain", this.domain, SearchOperation.EQUAL));
        }
        if (this.subDomain != null) {
            f.add(new SearchCriteria("subDomain", this.subDomain, SearchOperation.EQUAL));
        }
        if (this.type != null) {
            f.add(new SearchCriteria("typeIdCode", this.typeIdCode, SearchOperation.EQUAL));
        }
        if (this.concept != null) {
            f.add(new SearchCriteria("concept", this.concept, SearchOperation.EQUAL));
        }
        if (this.reference != null) {
            f.add(new SearchCriteria("reference", this.reference, SearchOperation.EQUAL));
        }
        if (this.fullURI != null) {
            f.add(new SearchCriteria("fullURI", this.fullURI, SearchOperation.EQUAL));
        }
        if (this.isEntity != null) {
            f.add(new SearchCriteria("isEntity", this.isEntity, SearchOperation.EQUAL));
        }
        if (this.isProperty != null) {
            f.add(new SearchCriteria("isProperty", this.isProperty, SearchOperation.EQUAL));
        }
        if (this.isInstance != null) {
            f.add(new SearchCriteria("isInstance", this.isInstance, SearchOperation.EQUAL));
        }
        if (this.entityName != null) {
            f.add(new SearchCriteria("entityName", this.entityName, SearchOperation.EQUAL));
        }
        if (this.propertyName != null) {
            f.add(new SearchCriteria("propertyName", this.propertyName, SearchOperation.EQUAL));
        }
        return f;
    }

    public CanonicalURIFilter buildFilterByEntityUniqueProperties() {
        CanonicalURIFilter f = new CanonicalURIFilter();

        if (this.id != 0) {
            f.add(new SearchCriteria("id", this.id, SearchOperation.EQUAL));
        } else {
            if (this.domain != null) {
                f.add(new SearchCriteria("domain", this.domain, SearchOperation.EQUAL));
            }
            if (this.subDomain != null) {
                f.add(new SearchCriteria("subDomain", this.subDomain, SearchOperation.EQUAL));
            }
            if (this.type != null) {
                f.add(new SearchCriteria("typeIdCode", this.typeIdCode, SearchOperation.EQUAL));
            }
            if (this.concept != null) {
                f.add(new SearchCriteria("concept", this.concept, SearchOperation.EQUAL));
            }
            if (this.reference != null) {
                f.add(new SearchCriteria("reference", this.reference, SearchOperation.EQUAL));
            }
            if (this.entityName != null) {
                f.add(new SearchCriteria("entityName", this.entityName, SearchOperation.EQUAL));
            }
            if (this.propertyName != null) {
                f.add(new SearchCriteria("propertyName", this.propertyName, SearchOperation.EQUAL));
            }
        }

        return f;
    }

    public void merge(CanonicalURI other){
        this.isEntity = other.isEntity;
        this.isProperty = other.isProperty;
        this.isInstance = other.isInstance;
        this.fullURI = other.fullURI;
    }

    public void generateFullURL(){
        generateFullURL("http://$domain$/$sub-domain$/$type$/$concept$/$reference$");
    }

    public void generateFullURL(String uriSchema) {
        if (Utils.isValidString(this.domain)) {
            uriSchema = uriSchema.replaceFirst("\\$domain\\$",this.domain);
        } else {
            throw new IllegalArgumentException("Domain field in CanonicalURI can´t be empty");
        }
        if (Utils.isValidString(this.subDomain)) {
            uriSchema = uriSchema.replaceFirst("\\$sub-domain\\$",this.subDomain);
        } else {
            uriSchema = uriSchema.replaceFirst("\\$main\\$",this.subDomain);
        }
        if (Utils.isValidString(this.type.getCode())) {
            uriSchema = uriSchema.replaceFirst("\\$type\\$",this.type.getCode());
        } else {
            throw new IllegalArgumentException("Type field in CanonicalURI can´t be empty");
        }
        if ( Utils.isValidString(this.concept)) {
            uriSchema = uriSchema.replaceFirst("\\$concept\\$",this.concept);
            if (Utils.isValidString(this.reference)) {
                uriSchema = uriSchema.replaceFirst("\\$reference\\$",this.reference);
            } else {
                if (isInstance || isProperty)
                    throw new IllegalArgumentException("Type field in CanonicalURI can´t be empty");
                else
                    uriSchema = uriSchema.replaceFirst("\\$reference\\$","");
            }
        } else {
            throw new IllegalArgumentException("Type field in CanonicalURI can´t be empty");
        }

        this.fullURI = uriSchema;
    }

    public void updateState(){
        if (this.propertyName!=null) {
            setIsProperty(true);
        } else if (reference!=null) {
            setIsInstance(true);
        } else {
            setIsEntity(true);
        }
    }

    @Override
    public String toString() {
        return "CanonicalURI{" +
                "id=" + id +
                ", domain='" + domain + '\'' +
                ", subDomain='" + subDomain + '\'' +
                ", typeIdCode='" + typeIdCode + '\'' +
                ", concept='" + concept + '\'' +
                ", reference='" + reference + '\'' +
                ", fullURI='" + fullURI + '\'' +
                ", isEntity=" + isEntity +
                ", isProperty=" + isProperty +
                ", isInstance=" + isInstance +
                ", entityName='" + entityName + '\'' +
                ", propertyName='" + propertyName + '\'' +
                '}';
    }
}