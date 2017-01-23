package com.teamtreehouse.model;
import java.util.Set;
import java.util.TreeSet;
import java.lang.Comparable;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList; 

public class Team implements Comparable<Team>{
 
  private String mTeamName;
  private String mCoach;
  private TreeSet<Player> mPlayers;
  
  public Team(String teamName, String coach){
    mTeamName = teamName;
    mCoach = coach;
    mPlayers = new TreeSet<Player>();
    
  }
  
  public void addPlayer(Player player){
    if(isPlayerAlreadyInTeam(player)){
     System.out.println("Not adding player to team as they are already in the team"); 
      return;
    }
    mPlayers.add(player);
    System.out.printf("Added %s %s to %s %n%n%n", player.getFirstName(), player.getLastName(), mTeamName);
  }
  
  public void removePlayer(Player player){
    mPlayers.remove(player);
  }
  
  public String getTeamName(){
   return mTeamName; 
  }
  
  public String getCoachName () {
   return mCoach; 
  }
  
  public boolean isPlayerAlreadyInTeam(Player player){
   return mPlayers.contains(player); 
  }
  
  public TreeSet<Player> getPlayers() {
   return mPlayers; 
  }
  
  public void printPlayers() {
   for(Player player: mPlayers){
    System.out.println(player.getFirstName() + " " + player.getLastName()); 
   }
  }
  
  @Override
  public int compareTo(Team team){
    Team other = (Team) team;
    if(equals(other)){
     return 0; 
    }
    
    int teamNameCompare = mTeamName.compareTo(team.mTeamName);
    return teamNameCompare;
  }
  
  public Map<String, ArrayList<Player>> getPlayersGroupedByHeight () {
    ArrayList<Player> smallPlayers = new ArrayList<Player> ();
    ArrayList<Player> averagePlayers = new ArrayList<Player> ();
    ArrayList<Player> tallPlayers = new ArrayList<Player> ();
    
    for(Player player: mPlayers){
     if(player.getHeightInInches() > 35 && player.getHeightInInches() <=40){
      smallPlayers.add(player); 
     } else if (player.getHeightInInches() > 40 && player.getHeightInInches() <=46) {
      averagePlayers.add(player); 
     } else {
      tallPlayers.add(player); 
     }
    }
    Map<String, ArrayList<Player>> heightMap = new HashMap<String, ArrayList<Player>> ();
    heightMap.put("35-40", smallPlayers);
    heightMap.put("41-46", averagePlayers);
    heightMap.put("47-50", tallPlayers);
    return heightMap;
  }
  
  public HashMap<String, ArrayList<Player>> getPlayersGroupedByExperience () {
   ArrayList<Player> experiencedPlayers = new ArrayList<Player>();
   ArrayList<Player> inexperiencedPlayers = new ArrayList<Player>();
    
   for(Player player: mPlayers){
     if(player.isPreviousExperience()){
       experiencedPlayers.add(player);
     } else {
      inexperiencedPlayers.add(player); 
     }
   }
    HashMap<String, ArrayList<Player>> players = new HashMap<String, ArrayList<Player>> ();
    players.put("experienced", experiencedPlayers);
    players.put("inexperienced", inexperiencedPlayers);
    return players;
  }
  
}