package rs.devlabs.api.db.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Miloš Stojković <milos@ast.co.rs>
 * 24.02.2023T14:16
 */
@Table(value = "threat_indicator")
public class ThreatIndicator {
    @Id
    private String id;
    @Column(value = "last_updated")
    private long lastUpdated;
    @Column(value = "created")
    private long created;

    public ThreatIndicator() {
    }

    public ThreatIndicator(String id, long lastUpdated, long created) {
        this.id = id;
        this.lastUpdated = lastUpdated;
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
