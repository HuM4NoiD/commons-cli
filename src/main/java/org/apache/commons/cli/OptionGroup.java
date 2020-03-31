/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.cli;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A group of mutually exclusive options.
 */
public class OptionGroup implements Serializable
{
    /** The serial version UID. */
    private static final long serialVersionUID = 1L;

    /** hold the options */
    private final Map<String, Option> optionMap = new LinkedHashMap<String, Option>();

    /** the name of the selected option */
    //Suppressed because an option is set while parsing, in DefaultParser at line 702
    @SuppressWarnings("initialization.fields.uninitialized")
    private @Nullable String selected;

    /** specified whether this group is required */
    private boolean required;

    /**
     * Add the specified <code>Option</code> to this group.
     *
     * @param option the option to add to this group
     * @return this option group with the option added
     */
    public OptionGroup addOption(final Option option)
    {
        // key   - option name
        // value - the option
        optionMap.put(option.getKey(), option);

        return this;
    }

    /**
     * @return the names of the options in this group as a
     * <code>Collection</code>
     */
//    OptionGroup.java:71: error: [return.type.incompatible] incompatible types in return.
//        return optionMap.keySet();
//                               ^
//  type of expression: Set<@KeyFor("this.optionMap") String>
//  method return type: Collection<String>
    public Collection<String> getNames()
    {
        // the key set is the collection of names
        return optionMap.keySet();
    }

    /**
     * @return the options in this group as a <code>Collection</code>
     */
    public Collection<Option> getOptions()
    {
        // the values are the collection of options
        return optionMap.values();
    }

    /**
     * Set the selected option of this group to <code>name</code>.
     *
     * @param option the option that is selected
     * @throws AlreadySelectedException if an option from this group has
     * already been selected.
     */
    public void setSelected(final Option option) throws AlreadySelectedException
    {
        if (option == null)
        {
            // reset the option previously selected
            selected = null;
            return;
        }

        // if no option has already been selected or the
        // same option is being reselected then set the
        // selected member variable
        if (selected == null || selected.equals(option.getKey()))
        {
            selected = option.getKey();
        }
        else
        {
            throw new AlreadySelectedException(this, option);
        }
    }

    /**
     * @return the selected option name
     */
    public @Nullable String getSelected()
    {
        return selected;
    }

    /**
     * @param required specifies if this group is required
     */
    public void setRequired(final boolean required)
    {
        this.required = required;
    }

    /**
     * Returns whether this option group is required.
     *
     * @return whether this option group is required
     */
    public boolean isRequired()
    {
        return required;
    }

    /**
     * Returns the stringified version of this OptionGroup.
     *
     * @return the stringified representation of this group
     */
    @Override
    public String toString()
    {
        final StringBuilder buff = new StringBuilder();

        final Iterator<Option> iter = getOptions().iterator();

        buff.append("[");

        while (iter.hasNext())
        {
            final Option option = iter.next();

            if (option.getOpt() != null)
            {
                buff.append("-");
                buff.append(option.getOpt());
            }
            else
            {
                buff.append("--");
                buff.append(option.getLongOpt());
            }

            if (option.getDescription() != null)
            {
                buff.append(" ");
                buff.append(option.getDescription());
            }

            if (iter.hasNext())
            {
                buff.append(", ");
            }
        }

        buff.append("]");

        return buff.toString();
    }
}
