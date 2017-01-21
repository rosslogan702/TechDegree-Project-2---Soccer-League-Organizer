package com.teamtreehouse.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Player; 

public class Season {
  
  private TreeSet<Team> mTeams;
  private ArrayList<Player> mPlayers; 
  
  public Season () {
    mTeams = new TreeSet<Team> ();
    mPlayers = new ArrayList<Player> (Arrays.asList(Players.load()));
  }
  
  public void addTeam(Team team) {
   mTeams.add(team); 
  }
  
  public TreeSet<Team> getTeams() {
   return mTeams; 
  }
  
  public Team getTeam(String teamName){
   for(Team team: mTeams){
    if(team.getTeamName().equals(teamName)){
      return team; 
    }
   }
    return null;
  }
  
  public boolean checkIfAbleToAddTeam() {
     return (mTeams.size() < mPlayers.size());
  }
  
}