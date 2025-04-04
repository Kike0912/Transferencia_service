
# Project NeoFundz :rocket:

## :loudspeaker: **Reglas Generales**
1. 

## :loudspeaker: Definición de las ramas de trabajo:
 - Todos los Pull Request (PR) deben llevar una revisión de un **compañero**.
 - No se puede crear un PR si el mismo marca conflictos, resolver los cambios de forma local y notificar el conflicto para evitar aglumeración de ajustes.
 - Se puede crear las ramas de trabajo necesarias para realizar sus cambios.

1. **Rama main (Producción):**
   - Los PR se realizarán cuando el producto es funcional.
2. **Rama release (MVP o Release X):**
   - Los PR se realizarán cuando el PO certifica la funcionalidad.

   > Ejemplo creación rama **(releasev1.0)**:
      > - Release/MVPv1.0
      > - Release/R01v1.0
3. **Rama de pruebas:**
- Los PR se realizarán cuando el conjunto de una o varias historias están listas para pruebas.
- Aquellas historias que cuenten con uno o más servicios se debe hacer pruebas unitarias de los métodos.
  - El porcentaje de aceptación de una prueba unitaria es del 80%.

  > Ejemplo creación rama **(release_sprint0x)**:
   >  - Feature/MVPv1.0_sprint01
   >  - Feature/R01v1.0_sprint01
4. **Rama de trabajo:**
- Rama creada para resolver los conflictos entre trabajos en comun con el equipo.
- Validar que después de resolver el conflicto que los cambios sigan funcionando.

  > Ejemplo creación rama **(wb-release_sprint0x)**:
   > - Custom/wb-MVPv1.0_sprint01
   > - Custom/wb-R01v1.0_sprint01

5. **Rama de trabajo personal:**
- Rama creadas para llevar los trabajos individuales.
- Se pueden crear más ramas de trabajo para fusionar los cambios antes de pasar wb-Release_Sprint_XX.
 
  > Ejemplo creación rama **(wb-release_sprint0x_Persona)**:
   > - Custom/wb-MVPv1.0_sprint01_carvaldes
   > - Custom/wb-R01v1.0_sprint01_carvaldes


  
