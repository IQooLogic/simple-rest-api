package rs.devlabs.api.db.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.devlabs.api.db.model.ThreatIndicator;

/**
 * @author Miloš Stojković <milos@ast.co.rs>
 * 24.02.2023T14:15
 */
@Repository
public interface ThreatIndicatorRepository extends CrudRepository<ThreatIndicator, String> {
}
