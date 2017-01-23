package com.teamtreehouse.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import com.teamtreehouse.model.Season;
import java.io.IOException;
import com.teamtreehouse.model.Team;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Player; 

public class Organizer {
  
  private Season mSeason;
  private Map<String, String> mMenu;
  private BufferedReader mReader;
  private ArrayList<Player> mAvailablePlayers;
  private static final String CREATE_TEAM = "1";
  private static final String ADD_PLAYER = "2";
  private static final String REMOVE_PLAYER = "3";
  private static final String HEIGHT_REPORT="4";
  private static final String LEAGUE_BALANCE_REPORT="5";
  private static final String PRINT_ROSTER="6";
  private static final String EXIT="7";
  private static final String DASH_SEPARATOR="================================================";
  
  public Organizer() {
   mSeason = new Season();
   mReader = new BufferedReader(new InputStreamReader(System.in));
   mMenu = new TreeMap<String, String>();
   mMenu.put(CREATE_TEAM, "Create a new team for the season");
   mMenu.put(ADD_PLAYER, "Add players to a team for the season");
   mMenu.put(REMOVE_PLAYER, "Remove a player from a team for the season");
   mMenu.put(HEIGHT_REPORT, "Generate a height report for a team");
   mMenu.put(LEAGUE_BALANCE_REPORT, "Generate a league balance report");
   mMenu.put(PRINT_ROSTER, "Print roster of a coach's team");
   mMenu.put(EXIT, "Exit the program");
   mAvailablePlayers = new ArrayList<Player> (Arrays.asList(Players.load()));
   Collections.sort(mAvailablePlayers);
  }
  
    public void run () {
    String choice = "";
    do {
     try{
       choice = promptForAction();
       switch(choice) {
        case CREATE_TEAM:             createTeam();
                                      break;
        case ADD_PLAYER:              addPlayer();
                                      break;
        case REMOVE_PLAYER:           removePlayer();
                                      break;
        case HEIGHT_REPORT:           heightReport();
                                      break;
        case LEAGUE_BALANCE_REPORT:   experienceReport();
                                      break;
        case PRINT_ROSTER:            printRoster();
                                      break;
        case EXIT:                    System.out.println("Thanks for playing");
                                      break;
        default:                      System.out.printf("Unknown choice: '%s'. Try again. %n%n%n"
                                                    , choice);
       }
     } catch (IOException ioe) {
        System.out.println("Problem with input");
        ioe.printStackTrace();
     }
    } while(!choice.equals(EXIT));
  }
  
  private String promptForAction() throws IOException{
    System.out.println(DASH_SEPARATOR);
    System.out.println("\nPlease select an option from the below");
    for(Map.Entry<String, String> option: mMenu.entrySet()) {
      System.out.printf("%s.)  %s %n", option.getKey(), option.getValue());
    }
    System.out.println(DASH_SEPARATOR + "\n");
    System.out.println("Please select your option: ");
    String choice = mReader.readLine();
    return choice.trim().toLowerCase();
  }
  
  private void createTeam() throws IOException{
     //TODO: Do not allow the same team with the same team name to be added twice
    if(mSeason.checkIfAbleToAddTeam()){
    Team team = promptForCreateTeam();
    boolean isValidTeam = checkValidTeamEntered(team);
      if(isValidTeam){
          mSeason.addTeam(team);
          System.out.printf("Added team %s with coach %s %n%n%n",
                            team.getTeamName(), team.getCoachName());
      } 
    } else {
      System.out.println("Too many teams. No more teams than players are allowed to be added. \n");
    }
  }
  
  private boolean checkValidTeamEntered(Team team) throws IOException {
    boolean isValidTeam = true;
    if (team==null) {
       System.out.println("Invalid team entered. " + 
                          "Please select from menu and try again. \n");
       return !isValidTeam;
    }
    return isValidTeam;
  }
  
  private boolean checkValidCoachEntered(String coachName) throws IOException {
    boolean isValidCoach = true;
    if(coachName==null){
     System.out.println("Invalid coach entered. " + 
                        "Please select print roster from menu and try again. \n"); 
     return !isValidCoach;
    }
     return isValidCoach;
  }
  
  private void addPlayer() throws IOException {
     Team team = promptForSelectTeam();
     boolean isValidTeamEntered = checkValidTeamEntered(team);
      if(isValidTeamEntered){
        Player player = promptForAddPlayer();
        boolean isValidPlayerEntered = checkIfValidPlayerEntered(player);
          if(isValidPlayerEntered){
            team.addPlayer(player);
            /* remove player from available players to be added to teams, since 
               the player has now been added to a team */
            int index = mAvailablePlayers.indexOf(player);
            mAvailablePlayers.remove(index);
      }
    }
  }
                        
  private void printRoster() throws IOException {
    String coachName = promptForCoach();
    boolean isValidCoachEntered = checkValidCoachEntered(coachName);
    if(isValidCoachEntered){
      for(Team teams: mSeason.getTeams()){
          System.out.println("\nPrinting " + coachName + " players: ");
          teams.printPlayers();
      }
    }
  }
  
  private void removePlayer() throws IOException {
     Team team = promptForSelectTeam();
     boolean isValidTeamEntered = checkValidTeamEntered(team);
      if(isValidTeamEntered) {
          Player player = promptForRemovePlayer(team);
          boolean isValidPlayerEntered = checkIfValidPlayerEntered(player);
            if(isValidPlayerEntered){
             team.removePlayer(player);
             System.out.printf("Removed %s %s from %s %n%n%n", 
                                player.getFirstName(), player.getLastName(), 
                                team.getTeamName());
             mAvailablePlayers.add(player);
             Collections.sort(mAvailablePlayers);
           }
    }
  }
  
  private void heightReport() throws IOException{
    Team team = promptForSelectTeam();
    boolean isValidTeamEntered = checkValidTeamEntered(team);
    if(isValidTeamEntered){
     printHeightReport(team); 
    }
  }
  
  public void printHeightReport(Team team) {
   Map<String, ArrayList<Player>> heightMap =team.getPlayersGroupedByHeight();
   for(Map.Entry<String, ArrayList<Player>> entry: heightMap.entrySet()){
     String heightRange = entry.getKey();
     ArrayList<Player> players = entry.getValue();
     System.out.println("\n" + players.size() + " players in height range " + heightRange + " inches, they are: ");
     for(Player player: players){
      System.out.println(player.getFirstName() + " " + player.getLastName()); 
     }
   }
  }
  
  private void experienceReport() throws IOException {
    printLeagueBalanceReport(mSeason.getTeams());
  }
  
  private Team promptForCreateTeam() throws IOException{
    System.out.println("Enter the team's name: ");
    String teamName = mReader.readLine();
    System.out.println("Enter the team's coach: ");
    String coachName = mReader.readLine();
    if(teamName.isEmpty() || coachName.isEmpty()){
     System.out.println("Either the team name or coach name entered were empty.");
     return null; 
    }
    return new Team(teamName, coachName);
  }
  
  private Team promptForSelectTeam() throws IOException {
   showListOfTeams();
   System.out.println("Enter the team's name: ");
   String teamName = mReader.readLine();
   return mSeason.getTeam(teamName);
  }
  
  private String promptForCoach() throws IOException {
   System.out.println("Enter coach name: ");
   String coachName = mReader.readLine();
   return coachName;
  }
  
  private Player promptForRemovePlayer() throws IOException {
   System.out.println("Enter the first and last name of the player you want to remove: ");
   String [] playerNames = mReader.readLine().split(" ");
   Player [] players = Players.load();
   for(Player player: players){
    if(player.getFirstName().equals(playerNames[0]) && player.getLastName().equals(playerNames[1])){
      return player; 
    }
   }
    return null;
  }
    
  private boolean checkIfValidPlayerEntered (Player player) {
    if(player == null){
     System.out.println("Invalid player entered. " +
                        "Please select add player from menu and try again. \n");
     return false; 
    }
    return true;
  }
  
  private Player promptForAddPlayer() throws IOException {
    showListOfPlayers();
    System.out.println("Enter the player's first and last name: ");
    String [] playerNames = mReader.readLine().split(" ");
    for(Player player: mAvailablePlayers){
     if(player.getFirstName().equals(playerNames[0]) && player.getLastName().equals(playerNames[1])){
      return player; 
     }
    }
    return null;
  }
  
  private Player promptForRemovePlayer(Team team) throws IOException {
    showListOfPlayers(team);
    System.out.println("Enter the player's first and last name: ");
    String [] playerNames = mReader.readLine().split(" ");
    TreeSet<Player> players = team.getPlayers();
    for(Player player: players){
     if(player.getFirstName().equals(playerNames[0]) && player.getLastName().equals(playerNames[1])){
      return player; 
     }
    }
    return null;
  }
  
  private void showListOfTeams() {
    TreeSet<Team> teams = mSeason.getTeams();
    System.out.println("Please select from one of the following teams: ");
    for(Team team: teams){
     System.out.println(team.getTeamName());
    }
  }
  
  private void showListOfPlayers () {
   System.out.println("Please select a player from the following list: ");
   System.out.println("First Name|Last Name|Height(inches)|Previous Experience \n");
   for(Player player: mAvailablePlayers){
    System.out.println(player.getFirstName() + "|" + player.getLastName() + "|" 
                     + player.getHeightInInches() + "|" + player.isPreviousExperience() + "|"); 
   }
  }
    
  private void showListOfPlayers(Team team){
    System.out.printf("List of players currently in %s are: %n", team.getTeamName());
    System.out.println("First Name|Last Name|Height(inches)|Previous Experience \n");
    Set<Player> teamPlayers = team.getPlayers();
    for(Player teamPlayer: teamPlayers){
          System.out.println(teamPlayer.getFirstName() + "|" + teamPlayer.getLastName() + "|" 
                   + teamPlayer.getHeightInInches() + "|" + teamPlayer.isPreviousExperience() + "|");
    }
  }
  
  private void printLeagueBalanceReport(TreeSet<Team> teams){
    for(Team team: teams){
      System.out.println("\n======================================");
      System.out.println("Team: " + team.getTeamName());
      HashMap<String, ArrayList<Player>> playersMap = team.getPlayersGroupedByExperience();
      for(Map.Entry<String, ArrayList<Player>> entry: playersMap.entrySet()){
     String experience = entry.getKey();
     ArrayList<Player> players = entry.getValue();
     System.out.println("Number of " + experience + " players are: " + players.size());
     int percentage = 0;
     if(team.getPlayers().size()>0){
      percentage = ((players.size()*100)/team.getPlayers().size()); 
     }
     System.out.println("Percentage of " + experience + 
                       " players " + percentage + "%");
      }
      printHeightReport(team);
      System.out.println(DASH_SEPARATOR);
    }
  }
}
