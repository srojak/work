# Events And Listeners in srojak.core

## Notice

Copyright © 2026 Stephen Rojak.

This file is part of the srojak Java portfolio.

The srojak Java portfolio is free software: you can redistribute it and/or modify it
under the terms of the GNU General Public License as published by the Free Software Foundation,
version 3 of the License.

The srojak Java portfolio is distributed in the hope that it will be useful, 
but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this portfolio.
If not, see https://www.gnu.org/licenses/.

## Base Classes

### CoreEvent

The abstract class ``CoreEvent`` is the base class for all other events in the framework.
The class defines a abstract ``formatData`` method that derived events must implement 
so that their contents can be rendered using the ``toDataString`` method.

### ClassBearingCoreEvent

The abstract class ``ClassBearingCoreEvent`` is the base class for all events that require a class identifier.

## StateChangeEvent And Listener

## SequentialEvent And Listener

## ObjectOwnershipEvent and Listener

The ``ObjectOwnershipEvent`` is a class-bearing event to indicate that the originator has changed its ownership
of another object.
The class is the class of the owned object.
The object reference may not be ``null``.

The ``ObjectOwnershipListener`` has two methods, ``acquire`` and ``release``.

## LifeCycleEvent And Listener

The ``LifeCycleEvent`` is sent out by classes that have distinct life cycles,
of which other classes may have an interest.

Current life cycle events have markers defined for when:
+ The object is closed;
+ The object is ready to be displayed;
+ The object has an accessible value that has changed.

The ``LifeCycleListener`` has one method, ``receive``, which applies the event.

## ListChangeVerbEvent And Listener

The ``ListChangeVerbEvent`` is sent out by some lists that raise events when their contents change.
The event has a verb identifying the nature and scope of the change.
If the change involves addition or removal of a single member object, the event may also include the member.

The ``ListChangeVerbListener`` has one method, ``listChanged``, which applies the event.

