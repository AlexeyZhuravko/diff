import { z } from "zod"

export const eventFormSchema = z.object({
    title: z.string().min(3, 'Название должно содержать не менее 3 символов'),
    description: z.string().min(3, 'Описание должно содержать не менее 3 символов').max(400, 'Описание должно содержать не более 400 символов'),
    location: z.string().min(3, 'Местоположение должно содержать не менее 3 символов').max(400, 'Местоположение должно содержать не более 400 символов'),
    imageUrl: z.string(),
    startDateTime: z.date(),
    endDateTime: z.date(),
    categoryId: z.string(),
    price: z.string(),
    isFree: z.boolean(),
    url: z.string().url()
  })
  