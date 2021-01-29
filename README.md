# BiggzAdditions
 A collection of various misc functionalities for MC Spigot/Paper servers ran by ebiggz

## Dev
**Recommended IDE**: IntelliJ IDEA

**Java SDK**: 1.8

**Steps**:
- Clone repo
- Install maven deps
- Add included Residence.jar to local maven repo:
```
mvn install:install-file -Dfile=./dependencies/Residence.jar -DgroupId=com.bekvon -DartifactId=Residence -Dversion=4.9.2.3 -Dpackaging=jar -DgeneratePom=true
```
(If you're using ItelliJ IDEA, click the **Execute Maven Goal** button and paste in the above command)

**Building**
- Run the *package* Maven goal

