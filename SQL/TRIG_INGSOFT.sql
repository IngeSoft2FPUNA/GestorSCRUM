------------------------------------------------------
CREATE FUNCTION validar_sprint() returns trigger as $$
    DECLARE
        sprint_count int;
        active_count int;
    BEGIN
        active_count := (SELECT COUNT(*) 
                            FROM sprint 
                        WHERE sprint.f_fin IS NULL
                            AND sprint.id_proyecto = NEW.id_proyecto
        );
        sprint_count := (SELECT COUNT(*) 
                            FROM sprint 
                        WHERE sprint.id_sprint != NEW.id_sprint 
                            AND sprint.f_ini <= NEW.f_ini 
                            AND (sprint.f_fin > NEW.f_fin OR sprint.f_fin IS NULL)
                            AND sprint.id_proyecto = NEW.id_proyecto
        );
        IF (sprint_count != 0) THEN
            RAISE EXCEPTION 'El periodo entre % y % ya tiene un sprint asignado', NEW.f_ini, NEW.f_fin;
        END IF;
        IF (active_count != 0) THEN
            RAISE EXCEPTION 'Ya hay un sprint activo dentro del proyecto %', NEW.id_proyecto;
        END IF;
        RETURN NEW;
    END;
    $$ language plpgsql;

CREATE TRIGGER validar_sprint_trigger BEFORE INSERT OR UPDATE ON sprint
    FOR EACH ROW EXECUTE PROCEDURE validar_sprint();
------------------------------------------------------
CREATE OR REPLACE FUNCTION burndown_chart(IN in_id_proyecto CHARACTER VARYING,IN in_id_sprint NUMERIC) RETURNS INTEGER[] AS $$

DECLARE
	v_fecha_inicio DATE := CURRENT_DATE;
	v_fecha DATE := CURRENT_DATE;
	v_story_points_estimados INTEGER := 0;
	v_story_points_realizados INTEGER := 0;
	v_story_points_acumulados INTEGER := 0;
	v_story_points_faltantes INTEGER := 0;
	duracion INTEGER := 0;
	v_dia INTEGER := 1;
	burndown_array INTEGER[];
	item INTEGER := 1;
BEGIN
	--obtener la fecha inicial y cantidad de dias estimados del proyecto
	SELECT fecha_inicio, duracion_estimada, story_points_estimados
	INTO v_fecha_inicio, duracion,v_story_points_estimados
	FROM sprints 
	WHERE id_proyecto = in_id_proyecto
	AND id_sprint = in_id_sprint;

	--se inicializan los storypoins faltantes
	v_story_points_faltantes = v_story_points_estimados;

	RAISE NOTICE 'FECHA INICIO: %', v_fecha_inicio;
	RAISE NOTICE 'DURACION: %', duracion;
	RAISE NOTICE 'STORY POINS ESTIMADOS: %', v_story_points_estimados;

	v_fecha = v_fecha_inicio;
	
	FOR i IN 1..duracion LOOP

		--Consultar los story points realizados en una fecha
		SELECT  CASE WHEN SUM(story_points) IS NULL THEN 0 ELSE SUM(story_points) END
		INTO v_story_points_realizados
		FROM userstories 
		WHERE id_proyecto = in_id_proyecto 
		AND id_sprint = in_id_sprint 
		AND id_estados = 3
		AND fecha_modificacion = v_fecha;

		--se acumulan los stoy points
		v_story_points_acumulados = v_story_points_acumulados + v_story_points_realizados;

		--se actualizan los story potins faltantes
		v_story_points_faltantes = v_story_points_faltantes - v_story_points_realizados;

		
		--se imprime los story points realizados en la fecha
		RAISE NOTICE 'FECHA : %  DIA: % STORY POINTS ESTIMADOS: % STORY POINTS ACUMULADOS: % STORY POINTS FALTANTES: %', 
		v_fecha,
		v_dia,
		v_story_points_estimados,
		v_story_points_acumulados,
		v_story_points_faltantes; 

		--se inserta en el array
		burndown_array[i] = v_story_points_faltantes;

		--se actualiza la fecha para la proxima iteracion
		v_fecha = v_fecha + 1;
		v_dia = v_dia + 1;
		
	END LOOP;
	
	--se retrona el array
	return burndown_array;
END
$$ LANGUAGE plpgsql;