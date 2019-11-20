package service;

import java.util.HashMap;
import java.util.HashSet;

/**
 * This class would be used to store the file information and peer stats information,
 * It would contains:
 * 1. peer is online or offline
 * 2. fileName and who has this file
 */
public class Index {

    private HashMap<String, Boolean> peerStats;
    private HashMap<String, HashSet<String>> fileStats;

    public Index() {
        this.peerStats = new HashMap<>();
        this.fileStats = new HashMap<>();
    }

    public Index(HashMap<String, Boolean> peerStats, HashMap<String, HashSet<String>> fileStats) {
        this.peerStats = peerStats;
        this.fileStats = fileStats;
    }

    /**
     * @param peerId
     * @return true if the peer already registered, otherwise return false
     */
    public boolean peerLoginSuccess(String peerId) {
        if (peerStats.containsKey(peerId)) {
            peerStats.put(peerId, true);
            return true;
        }
        return false;
    }

    /**
     * @param peerId
     * @return true if the peer is a registered user, otherwise return false
     */
    public boolean peerLogoutSuccess(String peerId) {
        if (peerStats.containsKey(peerId)) {
            peerStats.put(peerId, false);
            return true;
        }
        return false;
    }


    /**
     * register a peer and a file into this system
     * @param peerId
     * @param fileName
     */
    public void peerRegister(String peerId, String fileName) {
        HashSet<String> peers;
        if (fileStats.containsKey(fileName)) {
            peers = fileStats.get(fileName);
            peers.add(peerId);
        } else {
            peers = new HashSet<>();
            peers.add(peerId);

        }
        peerStats.put(peerId, true);
        fileStats.put(fileName, peers);
    }

    /**
     * Search available peers who has the target file
     * @param fileName
     * @return a set of peerIds who has the file
     */
    public HashSet<String> searchFile(String fileName) {
        if (fileStats.containsKey(fileName)) {
            return fileStats.get(fileName);
        }
        return new HashSet<>();
    }

    /**
     * delete the peerId with a specified fileName
     * @param fileName
     * @param peerId
     */
    public void deleteFileInPeer(String fileName, String peerId) {
        if (fileStats.containsKey(fileName)) {
            HashSet<String> peers = fileStats.get(fileName);
            HashSet<String> updated = new HashSet<>();
            for (String id : peers) {
                if (!id.equals(peerId)) {
                    updated.add(id);
                }
            }
            fileStats.put(fileName, updated);
        }
    }

    /**
     *
     * @param peer
     * @return true if the peer is available, otherwise false
     */
    public boolean isAvailable(String peer) {
        return peerStats.get(peer);
    }

    public HashMap<String, Boolean> getPeerStats() {
        return peerStats;
    }

    public void setPeerStats(HashMap<String, Boolean> peerStats) {
        this.peerStats = peerStats;
    }

    public HashMap<String, HashSet<String>> getFileStats() {
        return fileStats;
    }

    public void setFileStats(HashMap<String, HashSet<String>> fileStats) {
        this.fileStats = fileStats;
    }
}
