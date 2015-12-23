package org.pipoware.pst.exp;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Test;
import static org.pipoware.pst.exp.PropertyIdentifier.PidTagIpmSubTreeEntryId;

/**
 *
 * @author Franck Arnulfo
 */
public class MessageStoreTest {

  @Test
  public void testPSTPages() throws URISyntaxException, IOException {
    Path path = Paths.get(getClass().getResource("/pstsdk/sample1.pst").toURI());
    PSTFile pstFile = new PSTFile(path);
    NDB ndb = new NDB(pstFile, pstFile.getHeader());
    PC pc = ndb.getPCFromNID(SpecialInternalNID.NID_MESSAGE_STORE);
    System.out.println("PC:" + ToStringHelper.formatMultiline(pc.toString()));
    PCItem pic = pc.getPCItemByPropertyIdentifier(PidTagIpmSubTreeEntryId);
    EntryID rootEntryId = new EntryID(pic.dataValue);
    System.out.println("rootEntryId : " + ToStringHelper.formatMultiline(rootEntryId.toString()));
    System.out.println("rootEntryId.nid : " + new NID(rootEntryId.nid));
    PC rootFolderPC = ndb.getPCFromNID(rootEntryId.nid);
    System.out.println("rootFolderPC: " + ToStringHelper.formatMultiline(rootFolderPC.toString()));
    int rootFolderHCnid = (rootEntryId.nid & 0xFFFFFFE0) | NID.NID_TYPE_HIERARCHY_TABLE;
    TC hc = ndb.getTCFromNID(rootFolderHCnid);
    Folder rootFolder = new Folder(rootFolderPC);
    System.out.println("rootFolder: " + rootFolder);
    System.out.println("hc :" + ToStringHelper.formatMultiline(hc.toString()));
    System.out.println("index 0 :" + ToStringHelper.formatMultiline(ndb.getPCFromNID(32866).toString()));
    System.out.println("index 1 :" + ToStringHelper.formatMultiline(ndb.getPCFromNID(32898).toString()));
    
  }

  @Test
  public void testFolder() throws URISyntaxException, IOException {
    Path path = Paths.get(getClass().getResource("/pstsdk/sample1.pst").toURI());
    PSTFile pstFile = new PSTFile(path);
    MessageStore messageStore = pstFile.getMessageStore();
    Folder rootFolder = messageStore.getRootFolder();
  }
}
