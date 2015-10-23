package org.apache.openjpa.util;

import java.io.ObjectStreamException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.SortedSet;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public class java$util$PriorityQueue$proxy extends PriorityQueue
  implements ProxyCollection
{
  private transient OpenJPAStateManager sm;
  private transient int field;
  private transient CollectionChangeTracker changeTracker;
  private transient Class elementType;

  public java$util$PriorityQueue$proxy(int paramInt)
  {
    super(paramInt);
  }

  public java$util$PriorityQueue$proxy(int paramInt, Comparator paramComparator)
  {
    super(paramInt, paramComparator);
  }

  public java$util$PriorityQueue$proxy(Collection paramCollection)
  {
    super(paramCollection);
  }

  public java$util$PriorityQueue$proxy(PriorityQueue paramPriorityQueue)
  {
    super(paramPriorityQueue);
  }

  public java$util$PriorityQueue$proxy(SortedSet paramSortedSet)
  {
    super(paramSortedSet);
  }

  public java$util$PriorityQueue$proxy()
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
    return new PriorityQueue((PriorityQueue)paramObject);
  }

  public Class getElementType()
  {
    return this.elementType;
  }

  public ProxyCollection newInstance(Class paramClass, Comparator paramComparator, boolean paramBoolean1, boolean paramBoolean2)
  {
    proxy localproxy = new proxy();
    localproxy.elementType = paramClass;
    if (paramBoolean1)
      localproxy.changeTracker = new CollectionChangeTrackerImpl(localproxy, true, false, paramBoolean2);
    return localproxy;
  }

  public boolean add(Object paramObject)
  {
    ProxyCollections.beforeAdd(this, paramObject);
    boolean bool = super.add(paramObject);
    return ProxyCollections.afterAdd(this, paramObject, bool);
  }

  public void clear()
  {
    ProxyCollections.beforeClear(this);
    super.clear();
  }

  public Iterator iterator()
  {
    Iterator localIterator = super.iterator();
    return ProxyCollections.afterIterator(this, localIterator);
  }

  public boolean remove(Object paramObject)
  {
    ProxyCollections.beforeRemove(this, paramObject);
    boolean bool = super.remove(paramObject);
    return ProxyCollections.afterRemove(this, paramObject, bool);
  }

  public Object poll()
  {
    ProxyCollections.beforePoll(this);
    Object localObject = super.poll();
    return ProxyCollections.afterPoll(this, localObject);
  }

  public boolean offer(Object paramObject)
  {
    ProxyCollections.beforeOffer(this, paramObject);
    boolean bool = super.offer(paramObject);
    return ProxyCollections.afterOffer(this, paramObject, bool);
  }

  public boolean addAll(Collection paramCollection)
  {
    return ProxyCollections.addAll(this, paramCollection);
  }

  public Object remove()
  {
    ProxyCollections.beforeRemove(this);
    Object localObject = super.remove();
    return ProxyCollections.afterRemove(this, localObject);
  }

  public boolean removeAll(Collection paramCollection)
  {
    return ProxyCollections.removeAll(this, paramCollection);
  }

  public boolean retainAll(Collection paramCollection)
  {
    return ProxyCollections.retainAll(this, paramCollection);
  }

  protected Object writeReplace()
    throws ObjectStreamException
  {
    return Proxies.writeReplace(this, true);
  }
}

/* Location:           C:\Users\srybak\dev\java\projects\3rd_party_customized\openjpa\branches\2.1.1\openjpa\target\openjpa-2.1.1-AXWAY-1\
 * Qualified Name:     org.apache.openjpa.util.java.util.PriorityQueue.proxy
 * JD-Core Version:    0.6.2
 */