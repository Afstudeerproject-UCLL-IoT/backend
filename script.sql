select puzzle_name,
	case 
		when (lag(completed, 1) over (order by start_t)) then (lag(end_t, 1) over (order by start_t)) 
		else null
	end as start_time,
	case 
		when completed then end_t 
		else null 
	end as end_time, 
	totaal as total_attempts
from (
	select p."name" as puzzle_name, ps."position", min("at") as start_t, max("at") as end_t, count(puzzle_name) as totaal, bool_or(success) as completed
	from puzzle_subscriber ps inner join puzzle p on (p.device_owner_id = ps.subscriber_device_id) left join puzzle_attempt pa on(p."name" = pa.puzzle_name and pa.game_session_id = 3)
	where ps.game_name = 'TestGame2'
	group by 1,2
	order by ps."position"
) as summary
