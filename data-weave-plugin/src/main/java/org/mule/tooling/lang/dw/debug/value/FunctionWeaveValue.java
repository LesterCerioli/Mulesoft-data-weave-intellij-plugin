package org.mule.tooling.lang.dw.debug.value;

import com.intellij.openapi.project.Project;
import com.intellij.util.PlatformIcons;
import com.intellij.xdebugger.frame.XCompositeNode;
import com.intellij.xdebugger.frame.XNamedValue;
import com.intellij.xdebugger.frame.XValue;
import com.intellij.xdebugger.frame.XValueChildrenList;
import com.intellij.xdebugger.frame.XValueNode;
import com.intellij.xdebugger.frame.XValuePlace;
import org.jetbrains.annotations.NotNull;
import org.mule.weave.v2.debugger.DebuggerFunction;

public class FunctionWeaveValue extends XValue {

  private DebuggerFunction debuggerValue;
  private Project project;

  public FunctionWeaveValue(Project project, DebuggerFunction debuggerValue) {
    this.debuggerValue = debuggerValue;
    this.project = project;
  }

  @Override
  public void computePresentation(@NotNull XValueNode xValueNode, @NotNull XValuePlace xValuePlace) {
    xValueNode.setPresentation(PlatformIcons.FUNCTION_ICON, null, "Function", true);
  }


  @Override
  public void computeChildren(@NotNull XCompositeNode node) {
    final XValueChildrenList list = new XValueChildrenList();
    final String[] innerElements = debuggerValue.parameters();
    for (int i = 0; i < innerElements.length; i++) {
      String innerElement = innerElements[i];
      list.add(new DummyMessageValueNode("Param[" + i + "]", innerElement));
    }
    node.addChildren(list, false);
    super.computeChildren(node);
  }

  private static class DummyMessageValueNode extends XNamedValue {
    private final String value;


    public DummyMessageValueNode(String name, String value) {
      super(name);
      this.value = value;

    }

    public void computePresentation(@NotNull XValueNode node, @NotNull XValuePlace place) {
      node.setPresentation(PlatformIcons.PARAMETER_ICON, null, value, false);
    }
  }
}
