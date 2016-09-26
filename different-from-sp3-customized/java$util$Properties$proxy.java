package org.apache.openjpa.util;

import java.io.InputStream;
import java.io.ObjectStreamException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public class java$util$Properties$proxy extends Properties
  implements ProxyMap
{
  private transient OpenJPAStateManager sm;
  private transient int field;
  private transient MapChangeTracker changeTracker;
  private transient Class keyType;
  private transient Class valueType;

  public java$util$Properties$proxy(Properties paramProperties)
  {
    super(paramProperties);
  }

  public java$util$Properties$proxy()
  {
  }

  public void setOwner(OpenJPAStateManager paramOpenJPAStateManager, int paramInt)
  {
    this.sm = paramOpenJPAStateManager;
    this.field = paramInt;
  }

  public OpenJPAStateManager getOwner()
  {
    return this.sm;
  }

  public int getOwnerField()
  {
    return this.field;
  }

  public Object clone()
  {
    Proxy localProxy = (Proxy)super.clone();
    localProxy.setOwner(null, 0);
    return localProxy;
  }

  public ChangeTracker getChangeTracker()
  {
    return this.changeTracker;
  }

  public Object copy(Object paramObject)
  {
    return new Properties((Properties)paramObject);
  }

  public Class getKeyType()
  {
    return this.keyType;
  }

  public Class getValueType()
  {
    return this.valueType;
  }

  public ProxyMap newInstance(Class paramClass1, Class paramClass2, Comparator paramComparator, boolean paramBoolean1, boolean paramBoolean2)
  {
    proxy localproxy = new proxy();
    localproxy.keyType = paramClass1;
    localproxy.valueType = paramClass2;
    if (paramBoolean1)
      localproxy.changeTracker = new MapChangeTrackerImpl(localproxy, paramBoolean2);
    return localproxy;
  }

  public Object setProperty(String paramString1, String paramString2)
  {
    boolean bool = ProxyMaps.beforeSetProperty(this, paramString1, paramString2);
    Object localObject = super.setProperty(paramString1, paramString2);
    return ProxyMaps.afterSetProperty(this, paramString1, paramString2, localObject, bool);
  }

  public void load(InputStream paramInputStream)
  {
    ProxyMaps.beforeLoad(this, paramInputStream);
    super.load(paramInputStream);
  }

  public void loadFromXML(InputStream paramInputStream)
  {
    ProxyMaps.beforeLoadFromXML(this, paramInputStream);
    super.loadFromXML(paramInputStream);
  }

  public Object get(Object paramObject)
  {
    boolean bool = ProxyMaps.beforeGet(this, paramObject);
    Object localObject = super.get(paramObject);
    return ProxyMaps.afterGet(this, paramObject, localObject, bool);
  }

  public Object put(Object paramObject1, Object paramObject2)
  {
    boolean bool = ProxyMaps.beforePut(this, paramObject1, paramObject2);
    Object localObject = super.put(paramObject1, paramObject2);
    return ProxyMaps.afterPut(this, paramObject1, paramObject2, localObject, bool);
  }

  public Collection values()
  {
    return ProxyMaps.values(this);
  }

  public void clear()
  {
    ProxyMaps.beforeClear(this);
    super.clear();
  }

  public Set entrySet()
  {
    Set localSet = super.entrySet();
    return ProxyMaps.afterEntrySet(this, localSet);
  }

  public void putAll(Map paramMap)
  {
    ProxyMaps.putAll(this, paramMap);
  }

  public Object remove(Object paramObject)
  {
    boolean bool = ProxyMaps.beforeRemove(this, paramObject);
    Object localObject = super.remove(paramObject);
    return ProxyMaps.afterRemove(this, paramObject, localObject, bool);
  }

  public Set keySet()
  {
    return ProxyMaps.keySet(this);
  }

  protected Object writeReplace()
    throws ObjectStreamException
  {
    return Proxies.writeReplace(this, true);
  }
}

/* Location:           C:\Users\srybak\dev\java\projects\3rd_party_customized\openjpa\branches\2.1.1\openjpa\target\openjpa-2.1.1-AXWAY-1\
 * Qualified Name:     org.apache.openjpa.util.java.util.Properties.proxy
 * JD-Core Version:    0.6.2
 */