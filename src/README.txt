Instructions:
Run the program.
Type in the name of an update strategy (or just use the default one) and click to add it to the two drop down lists. 
Type in the name of a paint strategy (or just use the default one) and click to add it to the paint strategies dropdown list.

Whichever update strategy is in the top dropdown, and whicher paint in the right dropdown, will be used when you make a new ball

Also, feel free to combine update strategies (and select them) using the provided gui controls. 
The format of the string YOU need to type in for any sort of 'updatestrategy.nameofthestrategyStrategy.java' is just 'nameofthestrategy'
So, for updatestrategy.BreathingStrategy.java, just type in 'Breathing'

The paintstrategies work basically the same - we have a giant list of them, which you can find in the paintstrategy package.
One example: for a ball or square paint strategy, type in 'Ball' or 'Square,' respectively
Another example: for an image of jupiter use paintstrategy.JupiterPaintStratetegy.java, just type in 'Jupiter'. 
Also, yes we do have working gifs (try Sonic or Lamb for the best looking ones). All our gifs and images DO stay upright correctly.


Simple Update Strategy types (just type in the string provided in this list):
Straight
Breathing
Curve
Color
Drunken
Wander

Interaction Based Update Strategy types (what we want to use for the 45 points worth!):
Elastic: The required elastic collision strategy
Spawn: A class that we went over in comp310 and added in
Police: The first of our custom interaction balls. This models how a police car might affect a traffic system, making all 
			balls within a certain range of it slow down (the balls then return to normal speed once passing out of range).
			This satisfies our first 'non-collision' ball to ball interaction.
			(Note that we could have just made a police ball that ONLY slows down the balls, and never allows them to speed up, the
			re-speeding was mainly for fun).
Wormhole: The second of our custom interaction balls. This ball simulates a wormhole, meaning that it will teleport anything that 
			that can fit inside of it. As such, any balls with a smaller radius that collide with this ball will be teleported. However,
			the wormhole ball will not move (also, we set its mass and velocity to all be 0). 
Blackhole: Our third custom interaction balls, this update strategy models how a singularity might affect our system. 
			It really does two things: First, it applies a increasingly large suction to all balls within a certain range. 
			THIS SUCTION WITHIN A CERTAIN RANGE IS THE IMPORTANT PART OF THIS STRATEGY, AND IS A 'NON-COLLISION' BALL TO BALL
			INTERACTION THAT (IF YOU LOOK CLOSELY AT THE BALL BEING SUCKED IN) CAN RESULT IN A COOL ORBIT-LIKE ELLIPSE-ISH PATTERN OF MOVEMENT
			Second, it does kill balls that collide with it. However, this is NOT the important part of the behavior - that's just to
	 		make it more realistic and look cool/nice! (After all, if we didn't kill all colliding balls, they might clutter up the screen)
	 		...Also, if you want to see some fun interactions, try making more than one of these! Or, try creating massive amounts of 
	 		other types of balls, you should be able to see a few cool planetary orbital-like trajectories before stuff gets sucked in.
	 		(Additionally, if this strategy causes a ball to grow in size upon killing another ball, then that means for some reason this class is 
	 		not up to date - in the most up to date, the killing collision doesn't affect the black hole ball's radius at all).

(Also, in addition to wormhole, we created another similar type of ball - TeleportOnCollideStrategy. Upon a collision with another ball, 
this ball teleports both itself and the other ball. Although we considered making them part of a type hierarchy based off of teleporting, we decided 
against it since we didn't want to couple those two balls together (in order to leave us free-er to edit one of them without affecting the other later on).




Other update strategies we made for fun and haven't mentioned (don't look too much at these please)!:
Brown (i.e. brownian motion - this one is super weird and interesting to watch!)
Speeding: This one goes back and forth between speeding at an insane level and moving slower. Sometimes this one will defy the police ball by speeding
			(or, more accurately, accelerating) too fast for the police ball to slow it down! 
Star and Planet: These have some earlier behavior we were thinking about using in our black hole ball. They are in varying states of completeness, and
	are really more for fun and messing around with - for a while we wanted to use the getMass() to do more realistic gravity calculations, but due to the
	way that velocity works and the rather 'small' size of the panel we are drawing everything on, these didn't quite pan out. Their is some fun orbiting
	and 'supernova' behavior that we were able to get halfway working in there, though!
	To be honest, we probably should have taken them out, but left them in in case we ever want to go back and try to make them work better (so, this is why
	they may or may not be in a 'proper' hierarchy; they aren't really finished, and we haven't decided exactly what we really want to be invariant!) Please 
	don't grade these/check if they follow proper style/OO conventions (because they probably don't, they are NOT intended to be a 'real' part of our submission).

