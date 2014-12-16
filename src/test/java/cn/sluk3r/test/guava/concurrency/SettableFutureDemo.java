package cn.sluk3r.test.guava.concurrency;

import com.google.common.util.concurrent.SettableFuture;
import org.junit.Test;

import java.util.Collection;

/**
 * Created by baiing on 2014/12/9.
 */
public class SettableFutureDemo {
    //http://www.programcreek.com/java-api-examples/index.php?api=com.google.common.util.concurrent.SettableFuture

//    @Test
//    public void addNodes(){
//        SettableFuture<TorqueNode> settableFuture= SettableFuture.create();
//        settableFuture.set(new TorqueNode("","","",11111,11198));
//        doReturn(settableFuture).when(torqueNodeManager).addNode(any(TorqueNodeConfiguration.class));
//        commandLineEvaluation.handleCommand("addnodes",new CommandLineArguments("1 1"));
//        verify(torqueNodeManager).addNode(argThat(new MatchesTorqueNodeConfiguration(null,1)));
//    }
//
//    @Test public void removeNodes(){
//        SettableFuture<TorqueNode> settableFuture=SettableFuture.create();
//        settableFuture.set(null);
//        doReturn(settableFuture).when(torqueNodeManager).removeNodes(anyCollection());
//        Collection<NodeInformation> allNodesFromRmi=new LinkedList<NodeInformation>();
//        allNodesFromRmi.add(new NodeInformationImpl("1",null));
//        when(rmiServerForApi.getAllNodes()).thenReturn(allNodesFromRmi);
//        commandLineEvaluation.handleCommand("removenode",new CommandLineArguments("1"));
//        ArgumentCaptor<Collection> nodeUuidsCaptor=ArgumentCaptor.forClass(Collection.class);
//        verify(torqueNodeManager).removeNodes(nodeUuidsCaptor.capture());
//        assertThat(nodeUuidsCaptor.getValue().size(),is(1));
//        assertThat((Collection<String>)nodeUuidsCaptor.getValue(),hasItem("1"));
//    }

}
