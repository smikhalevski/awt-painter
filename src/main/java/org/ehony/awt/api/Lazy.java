package org.ehony.awt.api;

/**
 * Generic interface for lazy-loaded resources.
 */
public interface Lazy<Type>
{
    
    Type load();
}
