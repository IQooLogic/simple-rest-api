package rs.devlabs.api.db.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.devlabs.api.db.model.ThreatSource;

/**
 * @author Miloš Stojković <milos@ast.co.rs>
 * 24.02.2023T16:51
 */
@Repository
public interface ThreatSourceRepository extends CrudRepository<ThreatSource, String> {
}
