package springsecurity.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import springsecurity.entity.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {

	@Transactional
	@Modifying
	@Query("UPDATE Meeting m SET m.display = :notDisplay WHERE m.id = :meetingId")
	public void changeDisplay(@Param("meetingId") int id, @Param("notDisplay") boolean notDisplay);

	@Transactional
	@Modifying
	@Query("UPDATE Meeting m SET m.lastUpdated = now() WHERE m.id = :id")
	public void setLastUpdatedToNow(@Param("id") int id);

}
