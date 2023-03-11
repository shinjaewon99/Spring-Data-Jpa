package study.springDataJpa.repository;

public interface NestedClosedProjections {

    String getUsername();
    TeamInfo getTeam();

    // Projections을 하는 동안 username과, teamname을 가져온다.
    interface TeamInfo{
        String getName();
    }
}
