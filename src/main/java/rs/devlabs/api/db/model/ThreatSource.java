package rs.devlabs.api.db.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Miloš Stojković <milos@ast.co.rs>
 * 24.02.2023T16:48
 */
@Table("threat_source")
public class ThreatSource {
    @Id
    private String id;// file/url
    private String regex;
    @Column("last_updated")
    private long lastUpdated;

    public ThreatSource() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
