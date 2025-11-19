package manager;

import resource.Resource;

public abstract class ResourceFactory {
  public abstract Resource createResource(String name, int deposit);
}

