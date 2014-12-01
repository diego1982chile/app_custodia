package test3.ncxchile.cl.soap;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
  public class JSONExclStrat implements ExclusionStrategy {

        public boolean shouldSkipClass(Class<?> arg0) {
            return false;
        }

        public boolean shouldSkipField(FieldAttributes f) {

            return (f.getName().indexOf("_resolvedKey") > 0);
            
        }

    }